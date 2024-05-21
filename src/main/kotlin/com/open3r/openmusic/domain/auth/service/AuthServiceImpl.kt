package com.open3r.openmusic.domain.auth.service

import com.open3r.openmusic.domain.auth.dto.request.AuthLoginRequest
import com.open3r.openmusic.domain.auth.dto.request.AuthReissueRequest
import com.open3r.openmusic.domain.auth.dto.request.AuthSignUpRequest
import com.open3r.openmusic.domain.auth.repository.RefreshTokenRepository
import com.open3r.openmusic.domain.user.domain.User
import com.open3r.openmusic.domain.user.domain.UserProvider
import com.open3r.openmusic.domain.user.domain.UserRole
import com.open3r.openmusic.domain.user.dto.response.UserResponse
import com.open3r.openmusic.domain.user.repository.UserRepository
import com.open3r.openmusic.global.error.CustomException
import com.open3r.openmusic.global.error.ErrorCode
import com.open3r.openmusic.global.security.UserSecurity
import com.open3r.openmusic.global.security.jwt.Jwt
import com.open3r.openmusic.global.security.jwt.JwtProvider
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AuthServiceImpl(
    private val authenticationManagerBuilder: AuthenticationManagerBuilder,
    private val jwtProvider: JwtProvider,
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val refreshTokenRepository: RefreshTokenRepository,
    private val userSecurity: UserSecurity
) : AuthService {
    @Transactional
    override fun signup(request: AuthSignUpRequest) {
        if (userRepository.existsByEmail(request.email)) throw CustomException(ErrorCode.USER_ALREADY_EXISTS)

        val user = User(
            name = request.name,
            email = request.email,
            password = passwordEncoder.encode(request.password),
            role = UserRole.USER,
            provider = UserProvider.DEFAULT,
            providerId = "",
            profileUrl = ""
        )

        userRepository.save(user)
    }

    @Transactional
    override fun login(request: AuthLoginRequest): Jwt {
        val user = userRepository.findByEmailAndProvider(request.email, UserProvider.DEFAULT) ?: throw CustomException(
            ErrorCode.USER_NOT_FOUND
        )

        if (!passwordEncoder.matches(request.password, user.password))
            throw CustomException(ErrorCode.INVALID_PASSWORD)


        val authenticationToken = UsernamePasswordAuthenticationToken(request.email, request.password)
        val authentication = authenticationManagerBuilder.`object`.authenticate(authenticationToken)
        val token = jwtProvider.generateToken(authentication)

        refreshTokenRepository.save(user.id!!, token.refreshToken)

        return token
    }

    @Transactional
    override fun reissue(request: AuthReissueRequest): Jwt {
        jwtProvider.validateToken(request.refreshToken)

        val authentication = jwtProvider.getAuthentication(request.accessToken)
        val user = userRepository.findByEmail(authentication.name) ?: throw CustomException(ErrorCode.USER_NOT_FOUND)
        val refreshToken =
            refreshTokenRepository.findByUserId(user.id!!) ?: throw CustomException(ErrorCode.INVALID_REFRESH_TOKEN)

        if (refreshToken != request.refreshToken) throw CustomException(ErrorCode.INVALID_REFRESH_TOKEN)

        val token = jwtProvider.generateToken(authentication)

        refreshTokenRepository.save(user.id, token.refreshToken)

        return token
    }

    @Transactional(readOnly = true)
    override fun getMe(): UserResponse {
        val user = userSecurity.user

        return UserResponse.of(user)
    }
}
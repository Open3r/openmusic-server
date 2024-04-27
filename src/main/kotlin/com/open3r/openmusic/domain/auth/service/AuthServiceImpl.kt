package com.open3r.openmusic.domain.auth.service

import com.open3r.openmusic.domain.auth.dto.request.AuthLoginRequest
import com.open3r.openmusic.domain.auth.dto.request.AuthReissueRequest
import com.open3r.openmusic.domain.auth.dto.request.AuthSignUpRequest
import com.open3r.openmusic.domain.auth.repository.RefreshTokenRepository
import com.open3r.openmusic.domain.user.domain.User
import com.open3r.openmusic.domain.user.domain.UserRole
import com.open3r.openmusic.domain.user.repository.UserRepository
import com.open3r.openmusic.global.jwt.Jwt
import com.open3r.openmusic.global.jwt.JwtProperties
import com.open3r.openmusic.global.jwt.JwtProvider
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.UnsupportedJwtException
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.concurrent.TimeUnit

@Service
class AuthServiceImpl(
    private val jwtProvider: JwtProvider,
    private val jwtProperties: JwtProperties,
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val authenticationManagerBuilder: AuthenticationManagerBuilder,
    private val refreshTokenRepository: RefreshTokenRepository,
    private val redisTemplate: StringRedisTemplate
) : AuthService {
    @Transactional
    override fun signup(request: AuthSignUpRequest) {
        val username = request.username
        val password = request.password
        val exists = userRepository.existsByUsername(username)

        if (exists) throw RuntimeException("User already exists")

        val entity = User(
            username = username,
            password = passwordEncoder.encode(password),
            role = UserRole.USER
        )

        userRepository.save(entity)
    }

    @Transactional
    override fun login(request: AuthLoginRequest): Jwt {
        val user = userRepository.findByUsername(request.username) ?: throw RuntimeException("User not found")

        if (!passwordEncoder.matches(
                request.password,
                user.password
            )
        ) throw RuntimeException("Password is incorrect")

        val authenticationToken = UsernamePasswordAuthenticationToken(request.username, request.password)
        val authentication = authenticationManagerBuilder.`object`.authenticate(authenticationToken)
        val token = jwtProvider.createToken(authentication)

        refreshTokenRepository.save(
            authentication.name,
            token.refreshToken,
            jwtProperties.refreshTokenExpiration,
            TimeUnit.MILLISECONDS
        )

        return token
    }

    @Transactional
    override fun reissue(request: AuthReissueRequest): Jwt {
        try {
            jwtProvider.validateToken(request.refreshToken)
        } catch (e: ExpiredJwtException) {
            throw RuntimeException("Expired refresh token")
        } catch (e: UnsupportedJwtException) {
            throw RuntimeException("Unsupported refresh token")
        }

        val authentication = jwtProvider.getAuthentication(request.accessToken)
        val refreshToken = redisTemplate.opsForValue().get(authentication.name)

        if (refreshToken != request.refreshToken) throw RuntimeException("Invalid refresh token")

        val token = jwtProvider.createToken(authentication)

        redisTemplate.opsForValue().set(authentication.name, token.refreshToken)

        return token
    }
}
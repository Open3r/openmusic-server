package com.open3r.openmusic.domain.auth.service

import com.open3r.openmusic.domain.auth.dto.request.AuthLoginRequest
import com.open3r.openmusic.domain.auth.dto.request.AuthReissueRequest
import com.open3r.openmusic.domain.auth.dto.request.AuthSignOutRequest
import com.open3r.openmusic.domain.auth.dto.request.AuthSignUpRequest
import com.open3r.openmusic.domain.auth.dto.response.AuthVerifyEmailNumberResponse
import com.open3r.openmusic.domain.auth.dto.response.AuthVerifyEmailResponse
import com.open3r.openmusic.domain.auth.repository.RefreshTokenRepository
import com.open3r.openmusic.domain.auth.repository.VerifyCodeRepository
import com.open3r.openmusic.domain.user.domain.User
import com.open3r.openmusic.domain.user.domain.UserProvider
import com.open3r.openmusic.domain.user.domain.UserRole
import com.open3r.openmusic.domain.user.domain.UserStatus
import com.open3r.openmusic.domain.user.dto.response.UserResponse
import com.open3r.openmusic.domain.user.repository.UserRepository
import com.open3r.openmusic.global.error.CustomException
import com.open3r.openmusic.global.error.ErrorCode
import com.open3r.openmusic.global.security.UserSecurity
import com.open3r.openmusic.global.security.jwt.Jwt
import com.open3r.openmusic.global.security.jwt.JwtProvider
import com.open3r.openmusic.global.security.jwt.JwtType
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.UnsupportedJwtException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.security.SecureRandom

@Service
class AuthServiceImpl(
    private val authenticationManagerBuilder: AuthenticationManagerBuilder,
    private val jwtProvider: JwtProvider,
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val refreshTokenRepository: RefreshTokenRepository,
    private val userSecurity: UserSecurity,
    private val verifyCodeRepository: VerifyCodeRepository,
    private val mailSender: JavaMailSender
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
            throw CustomException(ErrorCode.USER_PASSWORD_NOT_MATCH)


        val authenticationToken = UsernamePasswordAuthenticationToken(request.email, request.password)
        val authentication = authenticationManagerBuilder.`object`.authenticate(authenticationToken)
        val token = jwtProvider.generateToken(authentication)

        refreshTokenRepository.save(user.id!!, token.refreshToken)

        return token
    }

    @Transactional
    override fun reissue(request: AuthReissueRequest): Jwt {
        try {
            jwtProvider.validateToken(request.refreshToken)
        } catch (e: ExpiredJwtException) {
            println("Expired Jwt Exception")
            throw CustomException(ErrorCode.EXPIRED_REFRESH_TOKEN)
        } catch (e: UnsupportedJwtException) {
            println("Unsupported Jwt Exception")
            throw CustomException(ErrorCode.UNSUPPORTED_REFRESH_TOKEN)
        } catch (e: SecurityException) {
            println("Security Exception")
            throw CustomException(ErrorCode.INVALID_REFRESH_TOKEN)
        } catch (e: IllegalArgumentException) {
            println("Illegal Argument Exception")
            throw CustomException(ErrorCode.INVALID_REFRESH_TOKEN)
        }

        if (jwtProvider.getType(request.refreshToken) != JwtType.REFRESH) {
            println("Not Refresh Token")
            throw CustomException(ErrorCode.INVALID_REFRESH_TOKEN)
        }

        val authentication = jwtProvider.getAuthentication(request.refreshToken)
        val user = userRepository.findByEmail(authentication.name) ?: throw CustomException(ErrorCode.USER_NOT_FOUND)
        val refreshToken =
            refreshTokenRepository.findByUserId(user.id!!) ?: throw CustomException(ErrorCode.INVALID_REFRESH_TOKEN)

        if (refreshToken != request.refreshToken) throw CustomException(ErrorCode.INVALID_REFRESH_TOKEN)

        val token = jwtProvider.generateToken(authentication)

        refreshTokenRepository.save(user.id, token.refreshToken)

        return token
    }

    @Transactional
    override fun signout(request: AuthSignOutRequest) {
        val user = userSecurity.user
        val u = userRepository.findByIdOrNull(user.id!!) ?: throw CustomException(ErrorCode.USER_NOT_FOUND)

        if (passwordEncoder.matches(
                request.password,
                u.password
            )
        ) throw CustomException(ErrorCode.USER_PASSWORD_NOT_MATCH)

        u.status = UserStatus.DELETED

        userRepository.save(u)
    }

    @Transactional(readOnly = true)
    override fun getMe(): UserResponse {
        val user = userSecurity.user

        return UserResponse.of(user)
    }

    override fun verifyEmail(): AuthVerifyEmailResponse {
        val email = userSecurity.user.email

        if (!userRepository.existsByEmail(email)) throw CustomException(ErrorCode.USER_NOT_FOUND)

        val user = userRepository.findByEmail(email) ?: throw CustomException(ErrorCode.USER_NOT_FOUND)

        if (user.verified) throw CustomException(ErrorCode.USER_ALREADY_VERIFIED)

        val code = createCode()

        verifyCodeRepository.save(email, code)

        val message = mailSender.createMimeMessage()

        MimeMessageHelper(message, false, "UTF-8").apply {
            setTo(email)
            setSubject("OpenMusic 인증 번호")
            setText("인증 번호: $code")
        }

        mailSender.send(message)

        return AuthVerifyEmailResponse(true)
    }

    override fun verifyEmailNumber(code: String): AuthVerifyEmailNumberResponse {
        val email = userSecurity.user.email
        if (!userRepository.existsByEmail(email)) throw CustomException(ErrorCode.USER_NOT_FOUND)
        if (!verifyCodeRepository.existsByEmail(email)) throw CustomException(ErrorCode.CERTIFICATION_NUMBER_NOT_FOUND)
        if (verifyCodeRepository.findByEmail(email) != code) throw CustomException(ErrorCode.CERTIFICATION_NUMBER_NOT_MATCH)

        val user = userRepository.findByEmail(email) ?: throw CustomException(ErrorCode.USER_NOT_FOUND)
        user.verified = true

        verifyCodeRepository.deleteByEmail(email)

        return AuthVerifyEmailNumberResponse(true)
    }

    private fun createCode(): String {
        return String.format("%06d", SecureRandom.getInstanceStrong().nextInt(999999))
    }
}
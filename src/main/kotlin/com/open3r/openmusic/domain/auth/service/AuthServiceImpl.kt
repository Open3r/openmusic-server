package com.open3r.openmusic.domain.auth.service

import com.open3r.openmusic.domain.auth.dto.request.*
import com.open3r.openmusic.domain.auth.dto.response.AuthSendEmailResponse
import com.open3r.openmusic.domain.auth.repository.EmailCodeRepository
import com.open3r.openmusic.domain.auth.repository.RefreshTokenRepository
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
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.UnsupportedJwtException
import io.jsonwebtoken.security.SecurityException
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
    private val emailCodeRepository: EmailCodeRepository,
    private val mailSender: JavaMailSender
) : AuthService {
    @Transactional
    override fun signup(request: AuthSignUpRequest) {
        if (userRepository.existsByEmail(request.email)) throw CustomException(ErrorCode.USER_ALREADY_EXISTS)
        if (!emailCodeRepository.existsByEmail(request.email)) throw CustomException(ErrorCode.INVALID_EMAIL)
        if (emailCodeRepository.findByEmail(request.email) != request.emailCode) throw CustomException(ErrorCode.INVALID_EMAIL_CODE)

        val user = User(
            nickname = request.name,
            email = request.email,
            password = passwordEncoder.encode(request.password),
            role = UserRole.USER,
            provider = UserProvider.DEFAULT,
            providerId = "-1",
            avatarUrl = "https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.youtube.com%2F%40TeamAvatar&psig=AOvVaw3EcZDNXj1d0bVuDq3OlPbZ&ust=1716791569621000&source=images&cd=vfe&opi=89978449&ved=0CBIQjRxqFwoTCPiQyJHZqoYDFQAAAAAdAAAAABAE"
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
            throw CustomException(ErrorCode.EXPIRED_REFRESH_TOKEN)
        } catch (e: UnsupportedJwtException) {
            throw CustomException(ErrorCode.UNSUPPORTED_REFRESH_TOKEN)
        } catch (e: MalformedJwtException) {
            throw CustomException(ErrorCode.INVALID_REFRESH_TOKEN)
        } catch (e: SecurityException) {
            throw CustomException(ErrorCode.INVALID_REFRESH_TOKEN)
        } catch (e: IllegalArgumentException) {
            throw CustomException(ErrorCode.INVALID_REFRESH_TOKEN)
        }

        if (jwtProvider.getType(request.refreshToken) != JwtType.REFRESH) throw CustomException(ErrorCode.INVALID_REFRESH_TOKEN)

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

    override fun sendEmail(request: AuthSendEmailRequest): AuthSendEmailResponse {
        val email = request.email

        if (userRepository.existsByEmail(email)) throw CustomException(ErrorCode.USER_ALREADY_EXISTS)
        if (emailCodeRepository.existsByEmail(email)) {
            val code = emailCodeRepository.findByEmail(email) ?: throw CustomException(ErrorCode.EMAIL_CODE_NOT_FOUND)
            emailCodeRepository.save(email, code)

            return AuthSendEmailResponse(email = email)
        }

        val code = String.format("%06d", SecureRandom.getInstanceStrong().nextInt(999999))

        emailCodeRepository.save(email, code)

        val message = mailSender.createMimeMessage()

        MimeMessageHelper(message, false, "UTF-8").apply {
            setTo(email)
            setSubject("OpenMusic 인증 번호")
            setText("인증 번호: $code")
        }

        mailSender.send(message)

        return AuthSendEmailResponse(email = email)
    }
}
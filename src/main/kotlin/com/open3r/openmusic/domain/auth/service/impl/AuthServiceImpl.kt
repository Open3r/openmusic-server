package com.open3r.openmusic.domain.auth.service.impl

import com.open3r.openmusic.domain.auth.dto.request.AuthLoginRequest
import com.open3r.openmusic.domain.auth.dto.request.AuthReissueRequest
import com.open3r.openmusic.domain.auth.dto.request.AuthSignOutRequest
import com.open3r.openmusic.domain.auth.dto.request.AuthSignUpRequest
import com.open3r.openmusic.domain.auth.dto.response.GoogleTokenResponse
import com.open3r.openmusic.domain.auth.dto.response.GoogleUserInfoResponse
import com.open3r.openmusic.domain.auth.repository.RefreshTokenRepository
import com.open3r.openmusic.domain.auth.service.AuthService
import com.open3r.openmusic.domain.email.repository.EmailCodeRepository
import com.open3r.openmusic.domain.user.domain.entity.UserEntity
import com.open3r.openmusic.domain.user.domain.enums.UserProvider
import com.open3r.openmusic.domain.user.domain.enums.UserRole
import com.open3r.openmusic.domain.user.domain.enums.UserStatus
import com.open3r.openmusic.domain.user.repository.UserRepository
import com.open3r.openmusic.global.config.oauth2.OAuth2GoogleProperties
import com.open3r.openmusic.global.error.CustomException
import com.open3r.openmusic.global.error.ErrorCode
import com.open3r.openmusic.global.security.UserSecurity
import com.open3r.openmusic.global.security.jwt.dto.Jwt
import com.open3r.openmusic.global.security.jwt.enums.JwtType
import com.open3r.openmusic.global.security.jwt.provider.JwtProvider
import com.open3r.openmusic.logger
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.UnsupportedJwtException
import io.jsonwebtoken.security.SecurityException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.MediaType
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient

@Service
class AuthServiceImpl(
    private val jwtProvider: JwtProvider,
    private val userSecurity: UserSecurity,
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val emailCodeRepository: EmailCodeRepository,
    private val refreshTokenRepository: RefreshTokenRepository,
    private val oAuth2GoogleProperties: OAuth2GoogleProperties,
) : AuthService {
    @Transactional
    override fun signup(request: AuthSignUpRequest) {
        if (userRepository.existsByEmail(request.email)) throw CustomException(ErrorCode.USER_ALREADY_EXISTS)
        if (!emailCodeRepository.existsByEmail(request.email)) throw CustomException(ErrorCode.INVALID_EMAIL)
        if (emailCodeRepository.findByEmail(request.email) != request.emailCode) throw CustomException(ErrorCode.INVALID_EMAIL_CODE)

        val user = UserEntity(
            nickname = request.nickname,
            email = request.email,
            password = passwordEncoder.encode(request.password),
            role = UserRole.USER,
            provider = UserProvider.DEFAULT,
            providerId = "-1",
            avatarUrl = "https://t4.ftcdn.net/jpg/01/43/42/83/360_F_143428338_gcxw3Jcd0tJpkvvb53pfEztwtU9sxsgT.jpg"
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

        val token = jwtProvider.generateToken(user)

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

        val token = jwtProvider.generateToken(user)

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

    @Transactional
    override fun googleLogin(code: String): Jwt {
        logger().info("Google Login Start")
        logger().info("Code: $code")

        val token = WebClient.create("https://oauth2.googleapis.com")
            .post()
            .uri("/token")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .body(
                BodyInserters.fromFormData("code", code)
                    .with("client_id", oAuth2GoogleProperties.clientId)
                    .with("client_secret", oAuth2GoogleProperties.clientSecret)
                    .with("redirect_uri", oAuth2GoogleProperties.redirectUri)
                    .with("grant_type", "authorization_code")
            )
            .retrieve()

            .onRawStatus({ it == 400 }) {
                logger().info("Login Failed: 400")
                throw CustomException(ErrorCode.INVALID_GOOGLE_CODE)
            }
            .onRawStatus({ it == 401 }) {
                logger().info("Login Failed: 401")
                throw CustomException(ErrorCode.INVALID_GOOGLE_CODE)
            }
            .onRawStatus({ it == 403 }) {
                logger().info("Login Failed: 403")
                it.bodyToMono(String::class.java).map {
                    logger().info("login Failed Message: $it")
                }
                throw CustomException(ErrorCode.INVALID_GOOGLE_CODE)
            }
            .onRawStatus({ it == 404 }) {
                logger().info("Login Failed: 404")
                throw CustomException(ErrorCode.INVALID_GOOGLE_CODE)
            }
            .onRawStatus({ it == 405 }) {
                logger().info("Login Failed: 405")
                throw CustomException(ErrorCode.INVALID_GOOGLE_CODE)
            }
            .onRawStatus({ it == 409 }) {
                logger().info("Login Failed: 409")
                throw CustomException(ErrorCode.INVALID_GOOGLE_CODE)
            }
            .onStatus({ it.is4xxClientError }) {
                it.bodyToMono(String::class.java)
                    .map { _ -> CustomException(ErrorCode.INVALID_GOOGLE_CODE) }
            }
            .bodyToMono(GoogleTokenResponse::class.java)
            .block()

        val info = WebClient.create("https://www.googleapis.com")
            .get()
            .uri {
                it.path("/oauth2/v1/userinfo")
                    .queryParam("alt", "json")
                    .build()
            }
            .header("Authorization", "Bearer ${token?.accessToken}")
            .retrieve()
            .bodyToMono(GoogleUserInfoResponse::class.java)
            .block()

        if (info == null) {
            throw CustomException(ErrorCode.INVALID_GOOGLE_ACCESS_TOKEN)
        }

        if (userRepository.existsByEmailAndProviderIsNot(info.email, UserProvider.GOOGLE)) throw CustomException(
            ErrorCode.USER_ALREADY_EXISTS
        )

        var user = userRepository.findByEmailAndProvider(info.email, UserProvider.GOOGLE)

        user = if (user == null) {
            val newUser = UserEntity(
                nickname = info.name ?: "User",
                email = info.email,
                role = UserRole.USER,
                provider = UserProvider.GOOGLE,
                providerId = info.id,
                avatarUrl = info.picture,
                password = passwordEncoder.encode(""),
            )

            userRepository.save(newUser)
        } else {
            user.providerId = info.id
            user.avatarUrl = info.picture

            userRepository.save(user)
        }

        val jwt = jwtProvider.generateToken(user)

        refreshTokenRepository.save(user.id!!, jwt.refreshToken)

        return jwt
    }
}
package com.open3r.openmusic.global.security.jwt

import com.open3r.openmusic.domain.user.repository.UserRepository
import com.open3r.openmusic.global.error.CustomException
import com.open3r.openmusic.global.error.ErrorCode
import com.open3r.openmusic.global.security.CustomUserDetails
import io.jsonwebtoken.*
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import io.jsonwebtoken.security.SecurityException
import jakarta.servlet.http.HttpServletRequest
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import java.util.*

@Component
class JwtProvider(
    private val jwtProperties: JwtProperties,
    private val userRepository: UserRepository
) {
    private val key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtProperties.secretKey))

    fun generateToken(authentication: Authentication): Jwt {
        val authorities = authentication.authorities.joinToString("") { it.authority }
        val now = Date()
        val accessExpiration = Date(now.time + jwtProperties.accessTokenExpiration)
        val refreshExpiration = Date(now.time + jwtProperties.refreshTokenExpiration)

        val accessToken = Jwts.builder()
            .setHeaderParam(Header.JWT_TYPE, JwtType.ACCESS)
            .setSubject(authentication.name)
            .claim("auth", authorities)
            .setIssuedAt(now)
            .setExpiration(accessExpiration)
            .signWith(key, SignatureAlgorithm.HS256)
            .compact()

        val refreshToken = Jwts.builder()
            .setHeaderParam(Header.JWT_TYPE, JwtType.REFRESH)
            .setIssuedAt(now)
            .setExpiration(refreshExpiration)
            .signWith(key, SignatureAlgorithm.HS256)
            .compact()

        return Jwt(accessToken, refreshToken)
    }

    fun getAuthentication(accessToken: String): Authentication {
        val claims = parseClaims(accessToken)
        val user = userRepository.findByEmail(claims.subject) ?: throw CustomException(ErrorCode.USER_NOT_FOUND)
        val details = CustomUserDetails(user)

        return UsernamePasswordAuthenticationToken(details, "", details.authorities)
    }

    fun validateToken(token: String) {
        try {
            parseClaims(token)
        } catch (e: ExpiredJwtException) {
            throw CustomException(ErrorCode.EXPIRED_ACCESS_TOKEN)
        } catch (e: UnsupportedJwtException) {
            throw CustomException(ErrorCode.UNSUPPORTED_ACCESS_TOKEN)
        } catch (e: MalformedJwtException) {
            throw CustomException(ErrorCode.INVALID_ACCESS_TOKEN)
        } catch (e: SecurityException) {
            throw CustomException(ErrorCode.INVALID_ACCESS_TOKEN)
        } catch (e: IllegalArgumentException) {
            throw CustomException(ErrorCode.INVALID_ACCESS_TOKEN)
        }
    }

    fun resolveToken(request: HttpServletRequest): String? {
        val token = request.getHeader("Authorization")

        if (StringUtils.hasText(token) && token.startsWith("Bearer ")) {
            return token.substring(7)
        }

        return null
    }

    fun getType(token: String): JwtType {
        val claims = parseClaims(token)

        return JwtType.valueOf(claims[Header.JWT_TYPE] as String)
    }

    private fun parseClaims(accessToken: String): Claims {
        return try {
            Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(accessToken)
                .body
        } catch (e: Exception) {
            when (e) {
                is ExpiredJwtException -> throw CustomException(ErrorCode.EXPIRED_ACCESS_TOKEN)
                is UnsupportedJwtException -> throw CustomException(ErrorCode.UNSUPPORTED_ACCESS_TOKEN)
                is MalformedJwtException, is SecurityException, is IllegalArgumentException -> throw CustomException(
                    ErrorCode.INVALID_ACCESS_TOKEN
                )

                else -> {
                    e.printStackTrace()
                    throw CustomException(ErrorCode.UNKNOWN)
                }
            }
        }
    }
}
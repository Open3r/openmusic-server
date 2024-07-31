package com.open3r.openmusic.global.security.jwt.provider

import com.open3r.openmusic.domain.user.domain.entity.UserEntity
import com.open3r.openmusic.domain.user.repository.UserRepository
import com.open3r.openmusic.global.error.CustomException
import com.open3r.openmusic.global.error.ErrorCode
import com.open3r.openmusic.global.security.jwt.config.JwtProperties
import com.open3r.openmusic.global.security.jwt.details.JwtUserDetails
import com.open3r.openmusic.global.security.jwt.dto.Jwt
import com.open3r.openmusic.global.security.jwt.enums.JwtType
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
    private val userRepository: UserRepository,
) {
    private val key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtProperties.secretKey))

    fun generateToken(user: UserEntity): Jwt {
        val now = Date()
        val accessExpiration = Date(now.time + jwtProperties.accessTokenExpiration)
        val refreshExpiration = Date(now.time + jwtProperties.refreshTokenExpiration)

        val accessToken = Jwts.builder()
            .setHeaderParam(Header.JWT_TYPE, JwtType.ACCESS)
            .setSubject(user.email)
            .setIssuedAt(now)
            .setExpiration(accessExpiration)
            .signWith(key, SignatureAlgorithm.HS256)
            .compact()

        val refreshToken = Jwts.builder()
            .setHeaderParam(Header.JWT_TYPE, JwtType.REFRESH)
            .setSubject(user.email)
            .setIssuedAt(now)
            .setExpiration(refreshExpiration)
            .signWith(key, SignatureAlgorithm.HS256)
            .compact()

        return Jwt(accessToken, refreshToken)
    }

    fun getAuthentication(token: String): Authentication {
        val claims = parseClaims(token)
        val user = userRepository.findByEmail(claims.subject) ?: throw CustomException(ErrorCode.USER_NOT_FOUND)
        val details = JwtUserDetails(user)

        return UsernamePasswordAuthenticationToken(details, "", details.authorities)
    }

    fun validateToken(token: String) {
        Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).body
    }

    fun resolveToken(request: HttpServletRequest): String? {
        val token = request.getHeader("Authorization")

        if (StringUtils.hasText(token) && token.startsWith("Bearer ")) {
            return token.substring(7)
        }

        return null
    }

    fun getType(token: String): JwtType {
        return JwtType.valueOf(
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).header[Header.JWT_TYPE] as String
        )
    }

    private fun parseClaims(token: String): Claims {
        return try {
            Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .body
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
    }
}
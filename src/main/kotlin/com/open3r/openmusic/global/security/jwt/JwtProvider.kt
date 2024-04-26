package com.open3r.openmusic.global.security.jwt

import com.open3r.openmusic.domain.user.repository.UserRepository
import com.open3r.openmusic.global.security.CustomUserDetails
import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
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
    private val key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtProperties.secret))

    fun createToken(authentication: Authentication): Jwt {
        val authorities = authentication.authorities.joinToString(",") { it.authority }
        val now = Date()
        val accessExpiration = Date(now.time + jwtProperties.accessExpiration)
        val refreshExpiration = Date(now.time + jwtProperties.refreshExpiration)

        val accessToken = Jwts.builder()
            .setSubject(authentication.name)
            .claim("auth", authorities)
            .setIssuedAt(now)
            .setExpiration(accessExpiration)
            .signWith(key, SignatureAlgorithm.HS256)
            .compact()

        val refreshToken = Jwts.builder()
            .setIssuedAt(now)
            .setExpiration(refreshExpiration)
            .signWith(key, SignatureAlgorithm.HS256)
            .compact()

        return Jwt(accessToken, refreshToken)
    }

    fun getAuthentication(accessToken: String): Authentication {
        val claims = parseClaims(accessToken)
        val user = userRepository.findByUsername(claims.subject) ?: throw RuntimeException("User not found")
        val details = CustomUserDetails(user)

        return UsernamePasswordAuthenticationToken(details, "", details.authorities)
    }

    fun resolveToken(request: HttpServletRequest): String? {
        val token = request.getHeader("Authorization")

        return if (StringUtils.hasText(token) && token.startsWith("Bearer ")) {
            token.substring(7)
        } else {
            null
        }
    }

    fun validateToken(accessToken: String) {
        Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).body
    }

    private fun parseClaims(accessToken: String): Claims {
        return try {
            Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(accessToken)
                .body
        } catch (e: ExpiredJwtException) {
            return e.claims
        }
    }
}
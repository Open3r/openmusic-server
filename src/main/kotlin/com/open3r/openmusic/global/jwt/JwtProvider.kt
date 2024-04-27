package com.open3r.openmusic.global.jwt

import com.open3r.openmusic.domain.user.repository.UserRepository
import com.open3r.openmusic.global.security.CustomUserDetails
import io.jsonwebtoken.*
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
        val authorities = authentication.authorities.joinToString("") { it.authority }
        val now = Date()
        val accessTokenExpiration = Date(now.time + jwtProperties.accessExpiration)
        val refreshTokenExpiration = Date(now.time + jwtProperties.refreshExpiration)

        val accessToken = Jwts.builder()
            .setSubject(authentication.name)
            .claim("auth", authorities)
            .setIssuedAt(now)
            .setExpiration(accessTokenExpiration)
            .signWith(key, SignatureAlgorithm.HS256)
            .compact()

        val refreshToken = Jwts.builder()
            .setIssuedAt(now)
            .setExpiration(refreshTokenExpiration)
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

        if (StringUtils.hasText(token) && token.startsWith("Bearer ")) {
            return token.substring(7)
        }

        return null
    }

    fun validateToken(token: String) {
        Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).body
    }

    private fun parseClaims(accessToken: String): Claims {
        return try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).body
        } catch (e: ExpiredJwtException) {
            e.claims
        }
    }
}
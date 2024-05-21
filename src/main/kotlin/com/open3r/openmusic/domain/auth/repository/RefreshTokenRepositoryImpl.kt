package com.open3r.openmusic.domain.auth.repository

import com.open3r.openmusic.global.security.jwt.JwtProperties
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Repository
import java.util.concurrent.TimeUnit

@Repository
class RefreshTokenRepositoryImpl(
    private val redisTemplate: StringRedisTemplate,
    private val jwtProperties: JwtProperties
) : RefreshTokenRepository {
    override fun save(userId: Long, refreshToken: String) {
        redisTemplate.opsForValue()
            .set(userId.toString(), refreshToken, jwtProperties.refreshTokenExpiration, TimeUnit.MILLISECONDS)
    }

    override fun findByUserId(userId: Long): String? {
        return redisTemplate.opsForValue().get(userId.toString())
    }

    override fun deleteByUserId(userId: Long) {
        redisTemplate.delete(userId.toString())
    }

    override fun existsByUserId(userId: Long): Boolean {
        return redisTemplate.hasKey(userId.toString())
    }
}
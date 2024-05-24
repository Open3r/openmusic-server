package com.open3r.openmusic.domain.auth.repository

import com.open3r.openmusic.global.security.jwt.JwtProperties
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository
import java.util.concurrent.TimeUnit

@Repository
class RefreshTokenRepositoryImpl(
    private val redisTemplate: RedisTemplate<String, String>,
    private val jwtProperties: JwtProperties
) : RefreshTokenRepository {
    override fun save(userId: Long, refreshToken: String) {
        redisTemplate.opsForValue()
            .set("refreshToken:${userId}", refreshToken, jwtProperties.refreshTokenExpiration, TimeUnit.MILLISECONDS)
    }

    override fun findByUserId(userId: Long): String? {
        return redisTemplate.opsForValue().get("refreshToken:${userId}")
    }

    override fun deleteByUserId(userId: Long) {
        redisTemplate.delete("refreshToken:${userId}")
    }

    override fun existsByUserId(userId: Long): Boolean {
        return redisTemplate.hasKey("refreshToken:${userId}")
    }
}
package com.open3r.openmusic.domain.auth.repository

import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Repository
import java.util.concurrent.TimeUnit

@Repository
class RefreshTokenRepositoryImpl(
    val redisTemplate: StringRedisTemplate
) : RefreshTokenRepository {
    override fun save(username: String, refreshToken: String) {
        redisTemplate.opsForValue().set(username, refreshToken)
    }

    override fun save(username: String, refreshToken: String, timeout: Long, unit: TimeUnit) {
        redisTemplate.opsForValue().set(username, refreshToken, timeout, unit)
    }

    override fun findByUsername(username: String): String? {
        return redisTemplate.opsForValue().get(username)
    }

    override fun deleteByUsername(username: String) {
        redisTemplate.delete(username)
    }

    override fun existsByUsername(username: String): Boolean {
        return redisTemplate.hasKey(username)
    }
}
package com.open3r.openmusic.domain.auth.repository

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository

@Repository
class EmailTokenRepositoryImpl(
    private val redisTemplate: RedisTemplate<String, String>
) : EmailTokenRepository {
    override fun save(email: String, token: String) {
        redisTemplate.opsForValue().set("emailToken:$email", token)
    }

    override fun findByEmail(email: String): String? {
        return redisTemplate.opsForValue().get(email)
    }

    override fun deleteByEmail(email: String) {
        redisTemplate.delete(email)
    }

    override fun existsByEmail(email: String): Boolean {
        return redisTemplate.hasKey(email)
    }
}
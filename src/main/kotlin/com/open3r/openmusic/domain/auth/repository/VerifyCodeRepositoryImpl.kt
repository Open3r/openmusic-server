package com.open3r.openmusic.domain.auth.repository

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository

@Repository
class VerifyCodeRepositoryImpl(
    private val redisTemplate: RedisTemplate<String, String>
) : VerifyCodeRepository {
    override fun save(email: String, number: String) {
        redisTemplate.opsForValue().set("certificationNumber:${email}", number)
    }

    override fun findByEmail(email: String): String? {
        return redisTemplate.opsForValue().get("certificationNumber:${email}")
    }

    override fun deleteByEmail(email: String) {
        redisTemplate.delete("certificationNumber:${email}")
    }

    override fun existsByEmail(email: String): Boolean {
        return redisTemplate.hasKey("certificationNumber:${email}")
    }
}
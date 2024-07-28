package com.open3r.openmusic.domain.email.repository.impl

import com.open3r.openmusic.domain.email.repository.EmailCodeRepository
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository
import java.util.concurrent.TimeUnit

@Repository
class EmailCodeRepositoryImpl(
    private val redisTemplate: RedisTemplate<String, String>
) : EmailCodeRepository {
    override fun save(email: String, code: String) {
        redisTemplate.opsForValue().set("emailCode:${email}", code, 3, TimeUnit.MINUTES)
    }

    override fun findByEmail(email: String): String? {
        return redisTemplate.opsForValue().get("emailCode:${email}")
    }

    override fun deleteByEmail(email: String) {
        redisTemplate.delete("emailCode:${email}")
    }

    override fun existsByEmail(email: String): Boolean {
        return redisTemplate.hasKey("emailCode:${email}")
    }
}
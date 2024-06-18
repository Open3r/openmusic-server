package com.open3r.openmusic.global.config

import jakarta.annotation.PostConstruct
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.core.RedisTemplate

@Configuration
class RedisStartConfig(
    private val redisTemplate: RedisTemplate<String, String>
) {
    @PostConstruct
    fun init() {
        redisTemplate.connectionFactory?.connection?.commands()?.flushAll()
    }
}
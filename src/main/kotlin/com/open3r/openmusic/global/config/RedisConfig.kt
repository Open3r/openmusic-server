package com.open3r.openmusic.global.config

import com.open3r.openmusic.global.properties.RedisProperties
import jakarta.annotation.PostConstruct
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisPassword
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories
import org.springframework.data.redis.serializer.StringRedisSerializer

@Configuration
@EnableRedisRepositories
class RedisConfig(
    private val redisProperties: RedisProperties
) {
    @PostConstruct
    fun init() {
        redisTemplate().keys("*").forEach { redisTemplate().delete(it) }
    }

    @Bean
    fun redisConnectionFactory() = LettuceConnectionFactory(RedisStandaloneConfiguration().apply {
        hostName = redisProperties.host
        port = redisProperties.port
        password = RedisPassword.of(redisProperties.password)
    })

    @Bean
    fun redisTemplate() = RedisTemplate<String, String>().apply {
        connectionFactory = redisConnectionFactory()
        keySerializer = StringRedisSerializer()
        valueSerializer = StringRedisSerializer()
    }
}
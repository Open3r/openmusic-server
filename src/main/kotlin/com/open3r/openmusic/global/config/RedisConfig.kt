package com.open3r.openmusic.global.config

import com.open3r.openmusic.global.properties.RedisProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisPassword
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories

@Configuration
@EnableRedisRepositories
class RedisConfig(
    private val redisProperties: RedisProperties
) {
    @Bean
    fun redisConnectionFactory() = LettuceConnectionFactory(RedisStandaloneConfiguration().apply {
        hostName = redisProperties.host
        port = redisProperties.port
        password = RedisPassword.of(redisProperties.password)
    })

    @Bean
    fun redisTemplate() = StringRedisTemplate().apply {
        connectionFactory = redisConnectionFactory()
    }
}
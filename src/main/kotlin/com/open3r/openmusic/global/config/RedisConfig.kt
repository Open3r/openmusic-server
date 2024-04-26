package com.open3r.openmusic.global.config

import com.open3r.openmusic.global.redis.RedisProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
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
    fun redisConnectionFactory(): RedisConnectionFactory {
        val configuration = RedisStandaloneConfiguration()

        configuration.hostName = redisProperties.host
        configuration.port = redisProperties.port
        configuration.password = RedisPassword.of(redisProperties.password)

        return LettuceConnectionFactory(configuration)
    }

    @Bean
    fun redisTemplate() = StringRedisTemplate().apply {
        connectionFactory = redisConnectionFactory()
    }
}
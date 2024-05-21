package com.open3r.openmusic.global.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "spring.data.redis")
class RedisProperties(
    val host: String,
    val port: Int,
    val password: String
)
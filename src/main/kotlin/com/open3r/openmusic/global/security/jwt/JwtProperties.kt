package com.open3r.openmusic.global.security.jwt

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "jwt")
class JwtProperties(
    val secret: String,
    val accessExpiration: Long,
    val refreshExpiration: Long,
)
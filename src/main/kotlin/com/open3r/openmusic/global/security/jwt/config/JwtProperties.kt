package com.open3r.openmusic.global.security.jwt.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "jwt")
class JwtProperties(
    val secretKey: String,
    val accessTokenExpiration: Long,
    val refreshTokenExpiration: Long
)
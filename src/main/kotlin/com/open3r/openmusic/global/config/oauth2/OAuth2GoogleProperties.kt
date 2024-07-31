package com.open3r.openmusic.global.config.oauth2

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "oauth2.google")
data class OAuth2GoogleProperties(
    val clientId: String,
    val clientSecret: String,
    val redirectUri: String
)
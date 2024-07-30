package com.open3r.openmusic.global.config.admin

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "admin")
data class AdminProperties(
    val email: String,
    val password: String,
)
package com.open3r.openmusic.global.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "google")
data class GoogleProperties(
    var clientId: String,
    var clientSecret: String,
    var redirectUri: String
)
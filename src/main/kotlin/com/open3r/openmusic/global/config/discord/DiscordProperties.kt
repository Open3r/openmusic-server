package com.open3r.openmusic.global.config.discord

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "discord")
class DiscordProperties(
    val token: String,
    val channelId: String
)
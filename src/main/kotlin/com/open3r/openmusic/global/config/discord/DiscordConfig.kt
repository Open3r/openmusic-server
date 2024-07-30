package com.open3r.openmusic.global.config.discord

import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.entities.Activity
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DiscordConfig(
    private val discordProperties: DiscordProperties
) {
    @Bean
    fun jda() = JDABuilder
        .createLight(discordProperties.token)
        .setActivity(Activity.playing("https://openmusic.com"))
        .build()
}
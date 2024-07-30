package com.open3r.openmusic.global.config.discord

import com.open3r.openmusic.logger
import net.dv8tion.jda.api.JDA
import org.springframework.context.event.ContextStartedEvent
import org.springframework.context.event.ContextStoppedEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class DiscordListener(
    private val jda: JDA,
    private val discordProperties: DiscordProperties
) {
    @EventListener
    fun onStarted(event: ContextStartedEvent) {
        jda.getTextChannelById(discordProperties.channelId)?.sendMessage("서버가 시작되었습니다.")?.queue()
        logger().info("서버가 시작되었습니다.")
    }

    @EventListener
    fun onStopped(event: ContextStoppedEvent) {
        jda.getTextChannelById(discordProperties.channelId)?.sendMessage("서버가 종료되었습니다.")?.queue()
        logger().info("서버가 종료되었습니다.")
    }
}
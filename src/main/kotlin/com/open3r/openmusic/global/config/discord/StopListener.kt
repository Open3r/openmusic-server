package com.open3r.openmusic.global.config.discord

import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.JDA
import org.springframework.context.ApplicationListener
import org.springframework.context.event.ContextClosedEvent
import org.springframework.stereotype.Component
import java.time.Instant

@Component
class StopListener(
    private val jda: JDA,
    private val discordProperties: DiscordProperties
): ApplicationListener<ContextClosedEvent> {
    override fun onApplicationEvent(event: ContextClosedEvent) {
        jda.getTextChannelById(discordProperties.channelId)?.sendMessageEmbeds(
            EmbedBuilder()
                .setTitle("서버가 종료되었습니다.")
                .setColor(0xFF0000)
                .setTimestamp(Instant.now())
                .build()
        )?.queue()
    }
}
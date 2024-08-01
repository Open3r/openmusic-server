package com.open3r.openmusic.global.config.discord

import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.JDA
import org.springframework.boot.context.event.ApplicationStartedEvent
import org.springframework.context.ApplicationListener
import org.springframework.stereotype.Component
import java.time.Instant

@Component
class StartListener(
    private val jda: JDA,
    private val discordProperties: DiscordProperties
) : ApplicationListener<ApplicationStartedEvent> {
    override fun onApplicationEvent(event: ApplicationStartedEvent) {
        jda.getTextChannelById(discordProperties.channelId)?.sendMessageEmbeds(
            EmbedBuilder()
                .setTitle("서버가 시작되었습니다.")
                .setColor(0x00FF00)
                .setTimestamp(Instant.now())
                .build()
        )?.queue()
    }
}
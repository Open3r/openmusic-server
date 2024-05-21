package com.open3r.openmusic.global.util

import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel
import net.dv8tion.jda.api.utils.FileUpload
import java.awt.Color
import java.util.*

object DiscordUtil {
    fun sendException(channel: TextChannel, exception: Exception) {
        val message = exception.message

        channel.sendMessageEmbeds(
            EmbedBuilder().apply {
                setTitle("Exception")
                setColor(Color.red)
                setTimestamp(Date().toInstant())

                if (message != null && message.length <= 1024) {
                    setDescription(message)
                }
            }.build()
        ).queue()

        if (message != null && message.length > 1024) {
            channel.sendFiles(FileUpload.fromData(message.toByteArray(), "message.txt")).queue()
        }

        val trace = exception.stackTraceToString()
        channel.sendFiles(FileUpload.fromData(trace.toByteArray(), "trace.txt")).queue()
    }
}
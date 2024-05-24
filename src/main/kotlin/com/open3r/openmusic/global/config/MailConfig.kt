package com.open3r.openmusic.global.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.JavaMailSenderImpl
import java.util.*

@Configuration
class MailConfig(
    @param:Value("\${spring.mail.host}") private val host: String,
    @param:Value("\${spring.mail.port}") private val port: Int,
    @param:Value("\${spring.mail.username}") private val username: String,
    @param:Value("\${spring.mail.password}") private val password: String,
    @param:Value("\${spring.mail.properties.mail.smtp.auth}") private val auth: Boolean,
    @param:Value("\${spring.mail.properties.mail.smtp.starttls.enable}") private val starttls: Boolean,
    @param:Value("\${spring.mail.properties.mail.smtp.starttls.required}") private val starttlsRequired: Boolean,
    @param:Value("\${spring.mail.properties.mail.smtp.timeout}") private val timeout: Int
) {
    @Bean
    fun javaMailSender(): JavaMailSender {
        val mailSender = JavaMailSenderImpl()

        mailSender.host = host
        mailSender.port = port
        mailSender.username = username
        mailSender.password = password
        mailSender.defaultEncoding = "UTF-8"
        mailSender.javaMailProperties = Properties().apply {
            put("mail.smtp.auth", auth)
            put("mail.smtp.starttls.enable", starttls)
            put("mail.smtp.starttls.required", starttlsRequired)
            put("mail.smtp.timeout", timeout)
            put("mail.smtp.connectiontimeout", timeout)
            put("mail.smtp.writetimeout", timeout)
        }

        return mailSender
    }
}
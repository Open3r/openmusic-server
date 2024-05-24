package com.open3r.openmusic.global.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.mail.javamail.JavaMailSenderImpl
import java.util.*

@Configuration
class MailConfig {
    @Value("\${spring.mail.host}")
    private lateinit var host: String

    @Value("\${spring.mail.port}")
    private var port = 0

    @Value("\${spring.mail.username}")
    private lateinit var username: String

    @Value("\${spring.mail.password}")
    private lateinit var password: String

    @Value("\${spring.mail.properties.mail.smtp.auth}")
    private lateinit var auth: String

    @Value("\${spring.mail.properties.mail.smtp.starttls.enable}")
    private var starttlsEnabled = false

    @Value("\${spring.mail.properties.mail.smtp.starttls.required}")
    private var starttlsRequired = false

    @Value("\${spring.mail.properties.mail.smtp.connection-timeout}")
    private lateinit var connectionTimeout: String

    @Value("\${spring.mail.properties.mail.smtp.timeout}")
    private lateinit var timeout: String

    @Value("\${spring.mail.properties.mail.smtp.write-timeout}")
    private lateinit var writeTimeout: String

    @Bean
    fun mailSender() = JavaMailSenderImpl().apply {
        val c = this@MailConfig

        host = c.host
        port = c.port
        username = c.username
        password = c.password
        defaultEncoding = "UTF-8"
        javaMailProperties = Properties().apply {
            put("mail.smtp.auth", c.auth)
            put("mail.smtp.starttls.enable", c.starttlsEnabled)
            put("mail.smtp.starttls.required", c.starttlsRequired)
            put("mail.smtp.connectiontimeout", c.connectionTimeout)
            put("mail.smtp.timeout", c.timeout)
            put("mail.smtp.writetimeout", c.writeTimeout)
        }
    }
}
package com.open3r.openmusic.domain.mail.service

import com.open3r.openmusic.global.error.CustomException
import com.open3r.openmusic.global.error.ErrorCode
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service

@Service
class MailServiceImpl(
    private val mailSender: JavaMailSender
) : MailService {
    override fun sendMail(to: String, subject: String, text: String) {
        val message = SimpleMailMessage().apply {
            setTo(to)
            setSubject(subject)
            setText(text)
        }

        try {
            mailSender.send(message)
        } catch (e: RuntimeException) {
            throw CustomException(ErrorCode.MAIL_SEND_ERROR)
        }
    }
}
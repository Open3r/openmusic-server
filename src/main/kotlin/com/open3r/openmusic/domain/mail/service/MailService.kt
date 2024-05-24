package com.open3r.openmusic.domain.mail.service

interface MailService {
    fun sendMail(to: String, subject: String, text: String)
}
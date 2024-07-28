package com.open3r.openmusic.domain.email.service

import com.open3r.openmusic.domain.email.dto.request.EmailSendRequest
import com.open3r.openmusic.domain.email.dto.response.EmailCheckResponse
import com.open3r.openmusic.domain.email.dto.response.EmailSendResponse

interface EmailService {
    fun sendEmail(request: EmailSendRequest): EmailSendResponse
    fun checkEmail(email: String): EmailCheckResponse
}
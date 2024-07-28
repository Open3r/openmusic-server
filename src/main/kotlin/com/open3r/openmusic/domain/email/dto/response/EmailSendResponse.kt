package com.open3r.openmusic.domain.email.dto.response

data class EmailSendResponse(
    val email: String,
    val resend: Boolean
)
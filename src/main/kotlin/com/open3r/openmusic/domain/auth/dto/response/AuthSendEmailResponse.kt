package com.open3r.openmusic.domain.auth.dto.response

data class AuthSendEmailResponse(
    val email: String,
    val resend: Boolean
)
package com.open3r.openmusic.domain.auth.dto.response

data class AuthVerifyEmailResponse(
    val token: String,
    val success: Boolean
)
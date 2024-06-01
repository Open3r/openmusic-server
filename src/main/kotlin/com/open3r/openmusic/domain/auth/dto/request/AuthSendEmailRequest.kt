package com.open3r.openmusic.domain.auth.dto.request

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class AuthSendEmailRequest(
    @field:NotBlank
    @field:Email
    val email: String
)
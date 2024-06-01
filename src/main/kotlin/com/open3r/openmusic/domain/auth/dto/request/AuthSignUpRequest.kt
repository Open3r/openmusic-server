package com.open3r.openmusic.domain.auth.dto.request

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class AuthSignUpRequest(
    @field:NotBlank
    val name: String,

    @field:Email
    val email: String,
    @field:NotBlank
    @field:Size(min = 6, max = 6)
    val emailCode: String,
    @field:NotBlank
    @field:Size(min = 8)
    val password: String,
)
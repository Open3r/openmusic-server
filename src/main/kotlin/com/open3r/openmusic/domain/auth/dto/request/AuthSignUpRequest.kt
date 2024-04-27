package com.open3r.openmusic.domain.auth.dto.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class AuthSignUpRequest(
    @field:NotBlank
    @field:Size(min = 3, max = 16)
    val username: String,
    @field:NotBlank
    @field:Size(min = 8, max = 32)
    val password: String,
)
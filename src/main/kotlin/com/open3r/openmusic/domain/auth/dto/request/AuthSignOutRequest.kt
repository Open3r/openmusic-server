package com.open3r.openmusic.domain.auth.dto.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class AuthSignOutRequest(
    @field:NotBlank
    @field:Size(min = 8)
    val password: String
)
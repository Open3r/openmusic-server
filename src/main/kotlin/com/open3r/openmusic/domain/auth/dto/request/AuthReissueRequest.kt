package com.open3r.openmusic.domain.auth.dto.request

import jakarta.validation.constraints.NotBlank

data class AuthReissueRequest(
    @field:NotBlank
    val accessToken: String,
    @field:NotBlank
    val refreshToken: String
)
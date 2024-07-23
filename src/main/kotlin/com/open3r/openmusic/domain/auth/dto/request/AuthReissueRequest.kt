package com.open3r.openmusic.domain.auth.dto.request

import com.fasterxml.jackson.annotation.JsonCreator
import jakarta.validation.constraints.NotBlank

data class AuthReissueRequest @JsonCreator constructor(
    @field:NotBlank
    val refreshToken: String
)
package com.open3r.openmusic.domain.auth.dto.response

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class GoogleUserInfoResponse(
    val id: String,
    val email: String,
    val name: String?,
    val picture: String
)
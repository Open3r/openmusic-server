package com.open3r.openmusic.domain.auth.dto.response

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class GoogleTokenResponse(
    val accessToken: String,
    val expiresIn: Long,
    val tokenType: String,
    val scope: String,
    val idToken: String
)
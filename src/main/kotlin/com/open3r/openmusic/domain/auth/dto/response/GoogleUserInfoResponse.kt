package com.open3r.openmusic.domain.auth.dto.response

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class GoogleUserInfoResponse(
    val id: String,
    val email: String,
    val verifiedEmail: Boolean,
    val name: String,
    val givenName: String,
    val familyName: String,
    val picture: String
)
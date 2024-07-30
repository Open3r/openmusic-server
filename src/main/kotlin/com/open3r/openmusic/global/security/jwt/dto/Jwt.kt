package com.open3r.openmusic.global.security.jwt.dto

data class Jwt(
    val accessToken: String,
    val refreshToken: String
)
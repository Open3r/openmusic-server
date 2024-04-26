package com.open3r.openmusic.global.security.jwt

data class Jwt(
    val accessToken: String,
    val refreshToken: String
)
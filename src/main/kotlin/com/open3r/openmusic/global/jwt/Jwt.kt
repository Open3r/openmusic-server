package com.open3r.openmusic.global.jwt

data class Jwt(
    val accessToken: String,
    val refreshToken: String
)
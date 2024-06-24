package com.open3r.openmusic.domain.auth.service

interface KakaoService {
    fun getAccessToken(code: String): String?
    fun getUserInfo(accessToken: String): String?
}
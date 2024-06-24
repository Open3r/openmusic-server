package com.open3r.openmusic.domain.auth.service

interface GoogleService {
    fun getAccessToken(code: String): String?
    fun getUserInfo(accessToken: String): String?
}
package com.open3r.openmusic.domain.auth.service

import com.open3r.openmusic.domain.auth.dto.request.*
import com.open3r.openmusic.domain.auth.dto.response.AuthSendEmailResponse
import com.open3r.openmusic.global.security.jwt.Jwt

interface AuthService {
    fun signup(request: AuthSignUpRequest)
    fun login(request: AuthLoginRequest): Jwt
    fun reissue(request: AuthReissueRequest): Jwt
    fun signout(request: AuthSignOutRequest)

    fun sendEmail(request: AuthSendEmailRequest): AuthSendEmailResponse
}
package com.open3r.openmusic.domain.auth.service

import com.open3r.openmusic.domain.auth.dto.request.AuthLoginRequest
import com.open3r.openmusic.domain.auth.dto.request.AuthReissueRequest
import com.open3r.openmusic.domain.auth.dto.request.AuthSignOutRequest
import com.open3r.openmusic.domain.auth.dto.request.AuthSignUpRequest
import com.open3r.openmusic.global.security.jwt.dto.Jwt

interface AuthService {
    fun signup(request: AuthSignUpRequest)
    fun login(request: AuthLoginRequest): Jwt
    fun reissue(request: AuthReissueRequest): Jwt
    fun signout(request: AuthSignOutRequest)
}
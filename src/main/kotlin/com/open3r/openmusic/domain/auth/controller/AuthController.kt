package com.open3r.openmusic.domain.auth.controller

import com.open3r.openmusic.domain.auth.dto.request.AuthLoginRequest
import com.open3r.openmusic.domain.auth.dto.request.AuthReissueRequest
import com.open3r.openmusic.domain.auth.dto.request.AuthSignUpRequest
import com.open3r.openmusic.domain.auth.service.AuthService
import com.open3r.openmusic.global.common.BaseResponse
import com.open3r.openmusic.global.security.jwt.Jwt
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Tag(name = "인증", description = "Auth")
@RestController
@RequestMapping("/auth")
class AuthController(
    private val authService: AuthService
) {
    @Operation(summary = "회원가입")
    @PostMapping("/signup")
    fun signup(@Valid @RequestBody request: AuthSignUpRequest): ResponseEntity<BaseResponse<Unit>> {
        return BaseResponse(authService.signup(request), 201).toEntity()
    }

    @Operation(summary = "로그인")
    @PostMapping("/login")
    fun login(@Valid @RequestBody request: AuthLoginRequest): ResponseEntity<BaseResponse<Jwt>> {
        return BaseResponse(authService.login(request), 200).toEntity()
    }

    @Operation(summary = "토큰 재발급")
    @PostMapping("/reissue")
    fun reissue(@RequestBody request: AuthReissueRequest): ResponseEntity<BaseResponse<Jwt>> {
        return BaseResponse(authService.reissue(request), 200).toEntity()
    }

    @GetMapping("/me")
    fun getMe(): ResponseEntity<BaseResponse<Unit>> {
        return BaseResponse(Unit, 200).toEntity()
    }
}
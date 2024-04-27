package com.open3r.openmusic.domain.auth.controller

import com.open3r.openmusic.domain.auth.dto.request.AuthLoginRequest
import com.open3r.openmusic.domain.auth.dto.request.AuthReissueRequest
import com.open3r.openmusic.domain.auth.dto.request.AuthSignUpRequest
import com.open3r.openmusic.domain.auth.service.AuthService
import com.open3r.openmusic.global.jwt.Jwt
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@Tag(name = "인증", description = "Auth")
@RestController
@RequestMapping("/auth")
class AuthController(
    private val authService: AuthService,
) {
    @Operation(summary = "회원가입")
    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("isAnonymous()")
    fun signup(@Valid @RequestBody request: AuthSignUpRequest) {
        authService.signup(request)
    }

    @Operation(summary = "로그인")
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("isAnonymous()")
    fun login(@Valid @RequestBody request: AuthLoginRequest): Jwt {
        return authService.login(request)
    }

    @Operation(summary = "재발급")
    @PostMapping("/reissue")
    @ResponseStatus(HttpStatus.CREATED)
    fun reissue(@Valid @RequestBody request: AuthReissueRequest): Jwt {
        return authService.reissue(request)
    }
}
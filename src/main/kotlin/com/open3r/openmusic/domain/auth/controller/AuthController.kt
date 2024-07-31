package com.open3r.openmusic.domain.auth.controller

import com.open3r.openmusic.domain.auth.dto.request.AuthLoginRequest
import com.open3r.openmusic.domain.auth.dto.request.AuthReissueRequest
import com.open3r.openmusic.domain.auth.dto.request.AuthSignOutRequest
import com.open3r.openmusic.domain.auth.dto.request.AuthSignUpRequest
import com.open3r.openmusic.domain.auth.service.AuthService
import com.open3r.openmusic.global.common.dto.response.BaseResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
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
    @PreAuthorize("isAnonymous()")
    fun signup(@Valid @RequestBody request: AuthSignUpRequest) =
        BaseResponse(authService.signup(request), 201).toEntity()

    @Operation(summary = "로그인")
    @PostMapping("/login")
    @PreAuthorize("isAnonymous()")
    fun login(@Valid @RequestBody request: AuthLoginRequest) = BaseResponse(authService.login(request), 200).toEntity()

    @Operation(summary = "토큰 재발급")
    @PostMapping("/reissue")
    @PreAuthorize("isAnonymous()")
    fun reissue(@RequestBody request: AuthReissueRequest) = BaseResponse(authService.reissue(request), 200).toEntity()

    @Operation(summary = "회원 탈퇴")
    @DeleteMapping("/signout")
    @PreAuthorize("isAuthenticated()")
    fun signout(@RequestBody request: AuthSignOutRequest) = BaseResponse(authService.signout(request), 204).toEntity()

    @Operation(summary = "구글 로그인")
    @GetMapping("/google")
    @PreAuthorize("isAnonymous()")
    fun googleLogin(@RequestParam code: String) = BaseResponse(authService.googleLogin(code), 200).toEntity()
}
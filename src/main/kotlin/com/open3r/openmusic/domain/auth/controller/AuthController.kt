package com.open3r.openmusic.domain.auth.controller

import com.open3r.openmusic.domain.auth.dto.request.*
import com.open3r.openmusic.domain.auth.service.AuthService
import com.open3r.openmusic.domain.auth.service.GoogleService
import com.open3r.openmusic.global.common.BaseResponse
import com.open3r.openmusic.global.error.CustomException
import com.open3r.openmusic.global.error.ErrorCode
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
    private val googleService: GoogleService
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

    @Operation(summary = "이메일 인증")
    @PostMapping("/send")
    @PreAuthorize("isAnonymous()")
    fun sendEmail(@RequestBody request: AuthSendEmailRequest) =
        BaseResponse(authService.sendEmail(request), 201).toEntity()

    @GetMapping("/google")
    @PreAuthorize("isAnonymous()")
    fun google(@RequestParam code: String): String {
        val accessToken = googleService.getAccessToken(code) ?: throw CustomException(ErrorCode.INVALID_GOOGLE_CODE)

        return googleService.getUserInfo(accessToken) ?: throw CustomException(ErrorCode.INVALID_GOOGLE_CODE)
    }
}
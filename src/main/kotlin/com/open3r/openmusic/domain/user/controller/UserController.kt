package com.open3r.openmusic.domain.user.controller

import com.open3r.openmusic.domain.user.dto.response.UserResponse
import com.open3r.openmusic.global.security.UserSecurity
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "사용자", description = "User")
@RestController
@RequestMapping("/users")
class UserController(
    private val userSecurity: UserSecurity
) {
    @Operation(summary = "사용자 목록 조회")
    @GetMapping
    fun getUsers(): List<UserResponse> {
        return emptyList()
    }

    @Operation(summary = "내 정보 조회")
    @GetMapping("/me")
    fun getMe(): UserResponse {
        val user = userSecurity.user

        return UserResponse.of(user)
    }
}
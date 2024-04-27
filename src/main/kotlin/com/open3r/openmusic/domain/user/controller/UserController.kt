package com.open3r.openmusic.domain.user.controller

import com.open3r.openmusic.domain.user.dto.response.UserResponse
import com.open3r.openmusic.global.security.UserSecurity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class UserController(
    private val userSecurity: UserSecurity
) {
    @GetMapping
    fun getUsers(): List<UserResponse> {
        return emptyList()
    }

    @GetMapping("/me")
    fun getMe(): UserResponse {
        val user = userSecurity.user

        return UserResponse(
            user.username,
        )
    }

    @PostMapping
    fun createUser(): UserResponse {
        TODO()
    }
}
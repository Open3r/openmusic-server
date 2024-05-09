package com.open3r.openmusic.domain.user.service

import com.open3r.openmusic.domain.user.dto.response.UserResponse

interface UserService {
    fun getUsers(): List<UserResponse>
    fun getUser(userId: Long): UserResponse
    fun deleteUser(): UserResponse
}
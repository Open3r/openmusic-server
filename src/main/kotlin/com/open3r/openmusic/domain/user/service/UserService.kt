package com.open3r.openmusic.domain.user.service

import com.open3r.openmusic.domain.user.dto.response.UserResponse

interface UserService {
    fun getUser(userId: Long): UserResponse
    fun createUser(): UserResponse
    fun updateUser(): UserResponse
    fun deleteUser(): UserResponse
}
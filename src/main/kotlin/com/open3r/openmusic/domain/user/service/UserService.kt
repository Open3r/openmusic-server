package com.open3r.openmusic.domain.user.service

import com.open3r.openmusic.domain.user.dto.request.UserUpdateRequest
import com.open3r.openmusic.domain.user.dto.response.UserResponse

interface UserService {
    fun getUsers(): List<UserResponse>
    fun updateMe(request: UserUpdateRequest): UserResponse

    fun getUser(userId: Long): UserResponse
    fun deleteUser(userId: Long)
}
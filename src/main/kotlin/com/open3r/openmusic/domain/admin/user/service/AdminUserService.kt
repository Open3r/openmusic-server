package com.open3r.openmusic.domain.admin.user.service

import com.open3r.openmusic.domain.user.dto.response.UserResponse

interface AdminUserService {
    fun getUsers(): List<UserResponse>
    fun getDeletedUsers(): List<UserResponse>
    fun deleteUser(userId: Long)
}
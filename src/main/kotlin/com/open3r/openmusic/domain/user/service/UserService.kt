package com.open3r.openmusic.domain.user.service

import com.open3r.openmusic.domain.song.domain.enums.SongGenre
import com.open3r.openmusic.domain.user.dto.request.UserUpdateRequest
import com.open3r.openmusic.domain.user.dto.response.CheckEmailResponse
import com.open3r.openmusic.domain.user.dto.response.UserResponse

interface UserService {
    fun getUsers(): List<UserResponse>
    fun checkEmail(email: String): CheckEmailResponse

    fun getMe(): UserResponse
    fun updateMe(request: UserUpdateRequest): UserResponse

    fun addGenre(genre: SongGenre)
    fun removeGenre(genre: SongGenre)

    fun getUser(userId: Long): UserResponse
    fun deleteUser(userId: Long)
}
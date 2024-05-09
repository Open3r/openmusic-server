package com.open3r.openmusic.domain.user.dto.response

import com.open3r.openmusic.domain.user.domain.User

data class UserResponse(
    val username: String,
    val role: String
) {
    companion object {
        fun of(user: User) = UserResponse(
            username = user.username,
            role = user.role.name
        )
    }
}
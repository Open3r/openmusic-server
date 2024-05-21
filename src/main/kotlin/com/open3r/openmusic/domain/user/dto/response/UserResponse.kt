package com.open3r.openmusic.domain.user.dto.response

import com.open3r.openmusic.domain.user.domain.User
import com.open3r.openmusic.domain.user.domain.UserProvider

data class UserResponse(
    val id: Long,
    val name: String,
    val email: String,

    val provider: UserProvider,
    val profileUrl: String
) {
    companion object {
        fun of(user: User) = UserResponse(
            id = user.id!!,
            email = user.email,
            name = user.name,
            provider = user.provider,
            profileUrl = user.profileUrl
        )
    }
}
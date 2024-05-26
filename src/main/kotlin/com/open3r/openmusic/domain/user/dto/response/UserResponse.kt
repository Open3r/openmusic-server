package com.open3r.openmusic.domain.user.dto.response

import com.open3r.openmusic.domain.user.domain.User
import com.open3r.openmusic.domain.user.domain.UserProvider
import com.open3r.openmusic.domain.user.domain.UserRole
import com.open3r.openmusic.domain.user.domain.UserStatus

data class UserResponse(
    val id: Long,
    val nickname: String,
    val email: String,
    val provider: UserProvider,
    val avatarUrl: String,
    val verified: Boolean,
    val role: UserRole,
    val status: UserStatus
) {
    companion object {
        fun of(user: User) = UserResponse(
            id = user.id!!,
            email = user.email,
            nickname = user.nickname,
            provider = user.provider,
            avatarUrl = user.avatarUrl,
            verified = user.verified,
            role = user.role,
            status = user.status,
        )
    }
}
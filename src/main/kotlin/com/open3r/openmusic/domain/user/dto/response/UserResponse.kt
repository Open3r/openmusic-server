package com.open3r.openmusic.domain.user.dto.response

import com.open3r.openmusic.domain.song.domain.enums.SongGenre
import com.open3r.openmusic.domain.user.domain.entity.UserEntity
import com.open3r.openmusic.domain.user.domain.enums.UserProvider
import com.open3r.openmusic.domain.user.domain.enums.UserRole
import com.open3r.openmusic.domain.user.domain.enums.UserStatus
import java.time.LocalDateTime

data class UserResponse(
    val id: Long,
    val nickname: String,
    val email: String,
    val provider: UserProvider,
    val avatarUrl: String,
    val role: UserRole,
    val status: UserStatus,
    val genres: Set<SongGenre>,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
) {
    companion object {
        fun of(user: UserEntity) = UserResponse(
            id = user.id!!,
            email = user.email,
            nickname = user.nickname,
            provider = user.provider,
            avatarUrl = user.avatarUrl,
            role = user.role,
            status = user.status,
            genres = user.genres,
            createdAt = user.createdAt,
            updatedAt = user.updatedAt
        )
    }
}
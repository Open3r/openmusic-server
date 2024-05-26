package com.open3r.openmusic.domain.user.dto.request

data class UserUpdateRequest(
    val nickname: String?,
    val avatarUrl: String?
)
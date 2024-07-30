package com.open3r.openmusic.domain.user.dto.request

data class UserSetNowPlayingRequest(
    val songId: Long,
    val progress: Long
)
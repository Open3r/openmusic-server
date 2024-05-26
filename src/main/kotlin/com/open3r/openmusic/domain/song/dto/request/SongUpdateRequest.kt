package com.open3r.openmusic.domain.song.dto.request

data class SongUpdateRequest(
    val title: String?,
    val description: String?,
    val coverUrl: String?,
    val url: String?
)
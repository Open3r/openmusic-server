package com.open3r.openmusic.domain.song.dto.request

data class SongCreateLyricsRequest(
    val lyrics: String,
    val timestamp: Int
)
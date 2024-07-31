package com.open3r.openmusic.domain.song.dto.response

import com.open3r.openmusic.domain.song.domain.entity.SongLyricsEntity

data class SongLyricsResponse(
    val lyrics: String,
    val timestamp: Int
) {
    companion object {
        fun of(lyrics: SongLyricsEntity) = SongLyricsResponse(
            lyrics = lyrics.lyrics,
            timestamp = lyrics.timestamp
        )
    }
}
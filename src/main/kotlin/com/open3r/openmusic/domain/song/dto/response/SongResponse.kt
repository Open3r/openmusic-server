package com.open3r.openmusic.domain.song.dto.response

import com.open3r.openmusic.domain.song.domain.Song

data class SongResponse(
    val id: Long,
    val title: String
) {
    companion object {
        fun of(song: Song) = SongResponse(
            id = song.id!!,
            title = song.title
        )
    }
}
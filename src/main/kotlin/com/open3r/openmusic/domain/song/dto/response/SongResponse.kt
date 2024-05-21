package com.open3r.openmusic.domain.song.dto.response

import com.open3r.openmusic.domain.song.domain.Song

data class SongResponse(
    val id: Long,
    val title: String,
    val description: String,
    val url: String,
    val artistId: Long
) {
    companion object {
        fun of(song: Song) = SongResponse(
            id = song.id!!,
            title = song.title,
            description = song.description,
            url = song.url,
            artistId = song.artist.id!!
        )
    }
}
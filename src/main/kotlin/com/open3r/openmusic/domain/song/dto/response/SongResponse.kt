package com.open3r.openmusic.domain.song.dto.response

import com.open3r.openmusic.domain.song.domain.Song

data class SongResponse(
    val id: Long,
    val title: String,
    val description: String,
    val coverUrl: String,
    val url: String,
    val likes: List<Long>,
    val artistId: Long
) {
    companion object {
        fun of(song: Song) = SongResponse(
            id = song.id!!,
            title = song.title,
            description = song.description,
            coverUrl = song.coverUrl,
            url = song.url,
            likes = song.likes.map { it.id!! },
            artistId = song.artist.id!!
        )
    }
}
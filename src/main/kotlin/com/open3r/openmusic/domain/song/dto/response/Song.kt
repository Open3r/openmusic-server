package com.open3r.openmusic.domain.song.dto.response

import com.open3r.openmusic.domain.song.domain.entity.SongEntity

data class Song(
    val id: Long,
    val title: String,
    val url: String,
    val likes: List<Long>,
    val artistId: Long
) {
    companion object {
        fun of(song: SongEntity) = Song(
            id = song.id!!,
            title = song.title,
            url = song.url,
            likes = song.likes.map { it.id!! },
            artistId = song.artist.id!!
        )
    }
}
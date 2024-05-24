package com.open3r.openmusic.domain.playlist.dto.response

import com.open3r.openmusic.domain.playlist.domain.Playlist

data class PlaylistResponse(
    val id: Long,
    val title: String,

    val songs: List<Long>,
    val likes: List<Long>,
    val userId: Long
) {
    companion object {
        fun of(playlist: Playlist) = PlaylistResponse(
            id = playlist.id!!,
            title = playlist.title,

            songs = playlist.songs.map { it.id!! },
            likes = playlist.likes.map { it.id!! },
            userId = playlist.artist.id!!
        )
    }
}
package com.open3r.openmusic.domain.playlist.dto.response

import com.open3r.openmusic.domain.playlist.domain.Playlist

data class PlaylistResponse(
    val id: Long,

    val songs: List<Long>,
    val userId: Long
) {
    companion object {
        fun of(playlist: Playlist) = PlaylistResponse(
            id = playlist.id!!,
            songs = playlist.songs.map { it.id!! },
            userId = playlist.user.id!!
        )
    }
}
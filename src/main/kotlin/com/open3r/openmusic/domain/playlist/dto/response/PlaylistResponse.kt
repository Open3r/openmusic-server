package com.open3r.openmusic.domain.playlist.dto.response

import com.open3r.openmusic.domain.playlist.domain.Playlist
import com.open3r.openmusic.domain.song.dto.response.SongResponse

data class PlaylistResponse(
    val id: Long,
    val title: String,
    val description: String,
    val songs: List<SongResponse>
) {
    companion object {
        fun of(playlist: Playlist) = PlaylistResponse(
            id = playlist.id!!,
            title = playlist.title,
            description = playlist.description,
            songs = playlist.songs.map { SongResponse.of(it) }
        )
    }
}
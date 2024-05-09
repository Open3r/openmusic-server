package com.open3r.openmusic.domain.album.dto.response

import com.open3r.openmusic.domain.album.domain.Album
import com.open3r.openmusic.domain.song.dto.response.SongResponse

data class AlbumResponse(
    val id: Long,
    val title: String,
    val description: String,

    val songs: List<SongResponse>
) {
    companion object {
        fun of(album: Album) = AlbumResponse(
            id = album.id!!,
            title = album.title,
            description = album.description,
            songs = album.songs.map { SongResponse.of(it) }
        )
    }
}
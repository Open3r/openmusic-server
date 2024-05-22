package com.open3r.openmusic.domain.album.dto.response

import com.open3r.openmusic.domain.album.domain.Album

data class AlbumResponse(
    val id: Long
) {
    companion object {
        fun of(album: Album) = AlbumResponse(
            id = album.id!!
        )
    }
}
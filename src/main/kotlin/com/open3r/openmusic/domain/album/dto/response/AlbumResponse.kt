package com.open3r.openmusic.domain.album.dto.response

import com.open3r.openmusic.domain.album.domain.Album

data class AlbumResponse(
    val id: Long,
    val title: String,
    val songs: List<Long>,
    val likes: List<Long>,
    val userId: Long
) {
    companion object {
        fun of(album: Album) = AlbumResponse(
            id = album.id!!,
            title = album.title,
            songs = album.songs.map { it.id!! },
            likes = album.likes.map { it.id!! },
            userId = album.user.id!!
        )
    }
}
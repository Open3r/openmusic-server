package com.open3r.openmusic.domain.album.dto.response

import com.open3r.openmusic.domain.album.domain.entity.AlbumEntity

data class AlbumResponse(
    val id: Long,
    val title: String,
    val coverUrl: String,
    val likes: List<Long>,
    val userId: Long
) {
    companion object {
        fun of(album: AlbumEntity) = AlbumResponse(
            id = album.id!!,
            title = album.title,
            coverUrl = album.coverUrl,
            likes = album.likes.map { it.id!! },
            userId = album.artist.id!!
        )
    }
}
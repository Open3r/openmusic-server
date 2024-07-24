package com.open3r.openmusic.domain.album.dto.response

import com.open3r.openmusic.domain.album.domain.entity.AlbumEntity
import java.time.LocalDateTime

data class AlbumResponse(
    val id: Long,
    val title: String,
    val coverUrl: String,
    val likes: List<Long>,
    val songs: List<Long>,
    val artistId: Long,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
) {
    companion object {
        fun of(album: AlbumEntity) = AlbumResponse(
            id = album.id!!,
            title = album.title,
            coverUrl = album.coverUrl,
            likes = album.likes.map { it.id!! },
            songs = album.songs.map { it.id!! },
            artistId = album.artist.id!!,
            createdAt = album.createdAt!!,
            updatedAt = album.updatedAt!!
        )
    }
}
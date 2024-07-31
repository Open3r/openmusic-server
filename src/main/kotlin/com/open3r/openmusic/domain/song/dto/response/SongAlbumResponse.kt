package com.open3r.openmusic.domain.song.dto.response

import com.open3r.openmusic.domain.album.domain.entity.AlbumEntity
import com.open3r.openmusic.domain.user.dto.response.UserResponse
import java.time.LocalDateTime

data class SongAlbumResponse(
    val id: Long,
    val title: String,
    val description: String,
    val coverUrl: String,
    val artist: UserResponse,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
) {
    companion object {
        fun of(album: AlbumEntity): SongAlbumResponse {
            return SongAlbumResponse(
                id = album.id!!,
                title = album.title,
                description = album.description,
                coverUrl = album.coverUrl,
                artist = UserResponse.of(album.artist),
                createdAt = album.createdAt!!,
                updatedAt = album.updatedAt!!
            )
        }
    }
}
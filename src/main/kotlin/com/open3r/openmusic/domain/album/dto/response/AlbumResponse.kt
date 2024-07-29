package com.open3r.openmusic.domain.album.dto.response

import com.open3r.openmusic.domain.album.domain.entity.AlbumEntity
import com.open3r.openmusic.domain.song.dto.response.SongResponse
import com.open3r.openmusic.domain.user.domain.entity.UserEntity
import com.open3r.openmusic.domain.user.dto.response.UserResponse
import java.time.LocalDateTime

data class AlbumResponse(
    val id: Long,
    val title: String,
    val description: String,
    val coverUrl: String,
    val liked: Boolean,
    val likeCount: Long,
    val songs: List<SongResponse>,
    val artist: UserResponse,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
) {
    companion object {
        fun of(album: AlbumEntity, user: UserEntity? = null) = AlbumResponse(
            id = album.id!!,
            title = album.title,
            description = album.description,
            coverUrl = album.coverUrl,
            liked = user?.let { album.likes.any { it.id == user.id } } ?: false,
            likeCount = album.likes.size.toLong(),
            songs = album.songs.map { SongResponse.of(it.song, user = user) },
            artist = UserResponse.of(album.artist),
            createdAt = album.createdAt!!,
            updatedAt = album.updatedAt!!
        )
    }
}
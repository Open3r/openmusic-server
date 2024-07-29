package com.open3r.openmusic.domain.playlist.dto.response

import com.open3r.openmusic.domain.playlist.domain.entity.PlaylistEntity
import com.open3r.openmusic.domain.playlist.domain.enums.PlaylistScope
import com.open3r.openmusic.domain.song.dto.response.SongResponse
import com.open3r.openmusic.domain.user.domain.entity.UserEntity
import com.open3r.openmusic.domain.user.dto.response.UserResponse
import java.time.LocalDateTime

data class PlaylistResponse(
    val id: Long,
    val title: String,
    val coverUrl: String,
    val scope: PlaylistScope,
    val songs: List<SongResponse>,
//    val likes: List<Long>,
    val liked: Boolean,
    val likeCount: Long,
    val artist: UserResponse,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
) {
    companion object {
        fun of(playlist: PlaylistEntity, user: UserEntity? = null) = PlaylistResponse(
            id = playlist.id!!,
            title = playlist.title,
            coverUrl = playlist.coverUrl,
            scope = playlist.scope,
            songs = playlist.songs.map { SongResponse.of(it.song, user = user) },
//            likes = playlist.likes.map { it.id!! },
            liked = user?.let { playlist.likes.any { it.user.id == user.id } } ?: false,
            likeCount = playlist.likes.size.toLong(),
            artist = UserResponse.of(playlist.artist),
            createdAt = playlist.createdAt!!,
            updatedAt = playlist.updatedAt!!
        )
    }
}
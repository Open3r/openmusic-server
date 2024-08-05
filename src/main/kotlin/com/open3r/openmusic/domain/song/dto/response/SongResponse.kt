package com.open3r.openmusic.domain.song.dto.response

import com.open3r.openmusic.domain.album.domain.enums.AlbumScope
import com.open3r.openmusic.domain.album.domain.enums.AlbumStatus
import com.open3r.openmusic.domain.song.domain.entity.SongEntity
import com.open3r.openmusic.domain.song.domain.enums.SongGenre
import com.open3r.openmusic.domain.user.domain.entity.UserEntity
import com.open3r.openmusic.domain.user.dto.response.UserResponse
import java.time.LocalDateTime

data class SongResponse(
    val id: Long,
    val title: String,
    val url: String,
    val thumbnailUrl: String,
//    val likes: List<Long>,
    val liked: Boolean,
    val likeCount: Long,
    val status: AlbumStatus,
    val genre: SongGenre,
    val scope: AlbumScope,
    val artist: UserResponse,
    val lyrics: List<SongLyricsResponse>,
    val album: SongAlbumResponse,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
) {
    companion object {
        fun of(song: SongEntity, user: UserEntity? = null) = SongResponse(
            id = song.id!!,
            title = song.title,
            url = song.url,
//            likes = song.likes.map { it.id!! },
            liked = user?.let { song.likes.any { it.user.id == user.id } } ?: false,
            likeCount = song.likes.size.toLong(),
            status = song.status,
            genre = song.genre,
            scope = song.scope,
            thumbnailUrl = song.album.coverUrl,
            artist = UserResponse.of(song.artist),
            lyrics = song.lyrics.map { SongLyricsResponse.of(it) },
            album = SongAlbumResponse.of(song.album),
            createdAt = song.createdAt!!,
            updatedAt = song.updatedAt!!
        )
    }
}
package com.open3r.openmusic.domain.song.dto.response

import com.open3r.openmusic.domain.song.domain.enums.SongGenre
import com.open3r.openmusic.domain.album.domain.enums.AlbumScope
import com.open3r.openmusic.domain.song.domain.entity.SongEntity
import com.open3r.openmusic.domain.user.domain.entity.UserEntity
import com.open3r.openmusic.domain.user.dto.response.UserResponse

data class SongResponse(
    val id: Long,
    val title: String,
    val url: String,
    val thumbnailUrl: String,
//    val likes: List<Long>,
    val liked: Boolean,
    val likeCount: Long,
    val genre: SongGenre,
    val scope: AlbumScope,
    val artist: UserResponse
) {
    companion object {
        fun of(song: SongEntity, user: UserEntity? = null) = SongResponse(
            id = song.id!!,
            title = song.title,
            url = song.url,
//            likes = song.likes.map { it.id!! },
            liked = user?.let { song.likes.any { it.id == user.id } } ?: false,
            likeCount = song.likes.size.toLong(),
            genre = song.genre,
            scope = song.scope,
            thumbnailUrl = song.album.coverUrl,
            artist = UserResponse.of(song.artist)
        )
    }
}
package com.open3r.openmusic.domain.playlist.dto.request

import com.open3r.openmusic.domain.playlist.domain.Playlist
import com.open3r.openmusic.domain.user.domain.UserEntity

data class PlaylistCreateRequest(
    val title: String,
    val coverUrl: String
) {
    fun toEntity(user: UserEntity) = Playlist(
        title = title,
        coverUrl = coverUrl,
        artist = user
    )
}
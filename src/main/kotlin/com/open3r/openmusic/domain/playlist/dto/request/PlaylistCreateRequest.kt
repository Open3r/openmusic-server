package com.open3r.openmusic.domain.playlist.dto.request

import com.open3r.openmusic.domain.playlist.domain.Playlist
import com.open3r.openmusic.domain.user.domain.User

data class PlaylistCreateRequest(
    val title: String,
    val coverUrl: String
) {
    fun toEntity(user: User) = Playlist(
        title = title,
        coverUrl = coverUrl,
        artist = user
    )
}
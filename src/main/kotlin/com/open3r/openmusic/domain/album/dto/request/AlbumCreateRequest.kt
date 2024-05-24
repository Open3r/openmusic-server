package com.open3r.openmusic.domain.album.dto.request

import com.open3r.openmusic.domain.album.domain.Album
import com.open3r.openmusic.domain.user.domain.User

data class AlbumCreateRequest(
    val title: String
) {
    fun toEntity(user: User) = Album(
        title = title,
        artist = user
    )
}
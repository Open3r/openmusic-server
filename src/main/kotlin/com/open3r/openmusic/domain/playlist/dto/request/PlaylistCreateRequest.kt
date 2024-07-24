package com.open3r.openmusic.domain.playlist.dto.request

import com.open3r.openmusic.domain.playlist.domain.entity.PlaylistEntity
import com.open3r.openmusic.domain.playlist.domain.enums.PlaylistScope
import com.open3r.openmusic.domain.user.domain.UserEntity

data class PlaylistCreateRequest(
    val title: String,
    val coverUrl: String,
    val scope: PlaylistScope
) {
    fun toEntity(user: UserEntity) = PlaylistEntity(
        title = title,
        coverUrl = coverUrl,
        scope = scope,
        artist = user
    )
}
package com.open3r.openmusic.domain.playlist.dto.request

import com.open3r.openmusic.domain.playlist.domain.enums.PlaylistScope

data class PlaylistUpdateRequest(
    val title: String?,
    val coverUrl: String?,
    val scope: PlaylistScope?
)
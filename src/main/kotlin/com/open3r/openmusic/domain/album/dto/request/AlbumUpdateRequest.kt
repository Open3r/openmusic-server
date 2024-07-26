package com.open3r.openmusic.domain.album.dto.request

import com.open3r.openmusic.domain.album.domain.enums.AlbumScope
import com.open3r.openmusic.domain.song.domain.enums.SongGenre

data class AlbumUpdateRequest(
    val title: String?,
    val description: String?,
    val coverUrl: String?,
    val scope: AlbumScope?,
    val genre: SongGenre?
)
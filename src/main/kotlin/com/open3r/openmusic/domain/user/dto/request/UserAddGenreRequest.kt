package com.open3r.openmusic.domain.user.dto.request

import com.open3r.openmusic.domain.song.domain.enums.SongGenre

data class UserAddGenreRequest(
    val genre: SongGenre
)
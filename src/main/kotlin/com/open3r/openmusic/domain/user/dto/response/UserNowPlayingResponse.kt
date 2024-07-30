package com.open3r.openmusic.domain.user.dto.response

import com.open3r.openmusic.domain.song.dto.response.SongResponse

data class UserNowPlayingResponse(
    val song: SongResponse,
    val progress: Long
)
package com.open3r.openmusic.domain.admin.song.service

import com.open3r.openmusic.domain.song.dto.response.SongResponse

interface AdminSongService {
    fun getSongs(): List<SongResponse>
    fun deleteSong(songId: Long)
}
package com.open3r.openmusic.domain.song.service

import com.open3r.openmusic.domain.song.dto.request.SongCreateRequest
import com.open3r.openmusic.domain.song.dto.response.SongResponse

interface SongService {
    fun getSongs(): List<SongResponse>
    fun getSong(songId: Long): SongResponse
    fun createSong(request: SongCreateRequest): SongResponse
    fun deleteSong(songId: Long): SongResponse
}
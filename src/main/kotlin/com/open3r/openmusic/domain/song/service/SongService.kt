package com.open3r.openmusic.domain.song.service

import com.open3r.openmusic.domain.song.dto.request.SongCreateRequest
import com.open3r.openmusic.domain.song.dto.response.SongResponse
import com.open3r.openmusic.domain.song.dto.response.SongUploadResponse
import org.springframework.web.multipart.MultipartFile

interface SongService {
    fun getSongs(): List<SongResponse>
    fun getSong(songId: Long): SongResponse
    fun searchSong(query: String): List<SongResponse>
    fun createSong(request: SongCreateRequest): SongResponse
    fun deleteSong(songId: Long)
    fun uploadSong(file: MultipartFile): SongUploadResponse
}
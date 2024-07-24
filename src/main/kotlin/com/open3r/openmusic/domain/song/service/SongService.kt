package com.open3r.openmusic.domain.song.service

import com.open3r.openmusic.domain.song.dto.request.SongUpdateRequest
import com.open3r.openmusic.domain.song.dto.response.SongResponse

interface SongService {
    fun getSongs(): List<SongResponse>
    fun getPublicSongs(): List<SongResponse>
    fun getPrivateSongs(): List<SongResponse>
    fun getRankingSongs(): List<SongResponse>
    fun getMySongs(): List<SongResponse>
    fun getSong(songId: Long): SongResponse
    fun searchSong(query: String): List<SongResponse>
    fun updateSong(songId: Long, request: SongUpdateRequest)
    fun deleteSong(songId: Long)
    fun addSongLike(songId: Long)
    fun removeSongLike(songId: Long)
}
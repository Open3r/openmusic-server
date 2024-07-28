package com.open3r.openmusic.domain.song.service

import com.open3r.openmusic.domain.song.domain.enums.SongGenre
import com.open3r.openmusic.domain.song.dto.request.SongUpdateRequest
import com.open3r.openmusic.domain.song.dto.response.SongResponse
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface SongService {
    fun getSongs(pageable: Pageable): Page<SongResponse>
    fun getRankingSongs(): List<SongResponse>
    fun getGenreSongs(genre: SongGenre): List<SongResponse>
    fun getMySongs(): List<SongResponse>
    fun getSong(songId: Long): SongResponse
    fun searchSong(query: String): List<SongResponse>
    fun updateSong(songId: Long, request: SongUpdateRequest)
    fun deleteSong(songId: Long)
    fun addSongLike(songId: Long)
    fun removeSongLike(songId: Long)
}
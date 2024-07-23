package com.open3r.openmusic.domain.song.service

import com.open3r.openmusic.domain.song.dto.request.SongUpdateRequest
import com.open3r.openmusic.domain.song.dto.response.Song

interface SongService {
    fun getSongs(): List<Song>
    fun getSong(songId: Long): Song
    fun searchSong(query: String): List<Song>
    fun updateSong(songId: Long, request: SongUpdateRequest)
    fun deleteSong(songId: Long)
    fun addSongLike(songId: Long)
    fun removeSongLike(songId: Long)
}
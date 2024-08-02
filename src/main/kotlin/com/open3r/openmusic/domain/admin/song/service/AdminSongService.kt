package com.open3r.openmusic.domain.admin.song.service

import com.open3r.openmusic.domain.song.dto.response.SongResponse

interface AdminSongService {
    fun getSongs(): List<SongResponse>
    fun getPendingSongs(): List<SongResponse>
    fun getApprovedSongs(): List<SongResponse>
    fun getRejectedSongs(): List<SongResponse>
    fun getDeletedSongs(): List<SongResponse>

    fun approveSong(songId: Long)
    fun rejectSong(songId: Long)

    fun deleteSong(songId: Long)
}
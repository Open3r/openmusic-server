package com.open3r.openmusic.domain.admin.song.repository

import com.open3r.openmusic.domain.song.domain.entity.SongEntity

interface AdminSongQueryRepository {
    fun getPendingSongs(): List<SongEntity>
    fun getApprovedSongs(): List<SongEntity>
    fun getRejectedSongs(): List<SongEntity>
    fun getDeletedSongs(): List<SongEntity>
}
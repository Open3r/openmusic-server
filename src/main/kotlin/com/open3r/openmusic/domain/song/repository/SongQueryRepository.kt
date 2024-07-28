package com.open3r.openmusic.domain.song.repository

import com.open3r.openmusic.domain.song.domain.entity.SongEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface SongQueryRepository {
    fun getSongs(pageable: Pageable): Page<SongEntity>
}
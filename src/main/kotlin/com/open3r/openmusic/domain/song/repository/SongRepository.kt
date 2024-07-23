package com.open3r.openmusic.domain.song.repository

import com.open3r.openmusic.domain.song.domain.entity.SongEntity
import org.springframework.data.jpa.repository.JpaRepository

interface SongRepository : JpaRepository<SongEntity, Long> {
    fun findAllByTitleContainingIgnoreCase(title: String): List<SongEntity>
}
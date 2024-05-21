package com.open3r.openmusic.domain.song.repository

import com.open3r.openmusic.domain.song.domain.Song
import org.springframework.data.jpa.repository.JpaRepository

interface SongRepository : JpaRepository<Song, Long> {
    fun findAllByTitleContainingIgnoreCase(title: String): List<Song>
}
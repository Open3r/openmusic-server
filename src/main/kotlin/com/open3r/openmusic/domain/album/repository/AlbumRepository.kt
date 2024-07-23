package com.open3r.openmusic.domain.album.repository

import com.open3r.openmusic.domain.album.domain.entity.AlbumEntity
import org.springframework.data.jpa.repository.JpaRepository

interface AlbumRepository : JpaRepository<AlbumEntity, Long> {
    fun findAllByTitleContainingIgnoreCase(title: String): List<AlbumEntity>
}
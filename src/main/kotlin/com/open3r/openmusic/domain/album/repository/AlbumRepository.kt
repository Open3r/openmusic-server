package com.open3r.openmusic.domain.album.repository

import com.open3r.openmusic.domain.album.domain.Album
import org.springframework.data.jpa.repository.JpaRepository

interface AlbumRepository : JpaRepository<Album, Long> {
    fun findAllByTitleContainingIgnoreCase(title: String): List<Album>
}
package com.open3r.openmusic.domain.album.repository

import com.open3r.openmusic.domain.album.domain.entity.AlbumEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface AlbumQueryRepository {
    fun searchAlbums(query: String, pageable: Pageable): Page<AlbumEntity>
    fun getAlbums(pageable: Pageable): Page<AlbumEntity>
}
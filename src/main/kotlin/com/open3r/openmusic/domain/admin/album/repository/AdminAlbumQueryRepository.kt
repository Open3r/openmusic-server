package com.open3r.openmusic.domain.admin.album.repository

import com.open3r.openmusic.domain.album.domain.entity.AlbumEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface AdminAlbumQueryRepository {
    fun getPendingAlbums(pageable: Pageable): Page<AlbumEntity>
    fun getApprovedAlbums(pageable: Pageable): Page<AlbumEntity>
    fun getRejectedAlbums(pageable: Pageable): Page<AlbumEntity>
    fun getDeletedAlbums(pageable: Pageable): Page<AlbumEntity>
}
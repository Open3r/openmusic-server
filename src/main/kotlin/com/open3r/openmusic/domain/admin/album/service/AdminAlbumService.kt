package com.open3r.openmusic.domain.admin.album.service

import com.open3r.openmusic.domain.album.dto.response.AlbumResponse
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface AdminAlbumService {
    fun getPendingAlbums(pageable: Pageable): Page<AlbumResponse>
    fun getApprovedAlbums(pageable: Pageable): Page<AlbumResponse>
    fun getRejectedAlbums(pageable: Pageable): Page<AlbumResponse>
    fun getDeletedAlbums(pageable: Pageable): Page<AlbumResponse>

    fun approveAlbum(albumId: Long)
    fun rejectAlbum(albumId: Long)

    fun deleteAlbum(albumId: Long)
}
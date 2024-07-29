package com.open3r.openmusic.domain.album.service

import com.open3r.openmusic.domain.album.dto.request.AlbumCreateRequest
import com.open3r.openmusic.domain.album.dto.request.AlbumUpdateRequest
import com.open3r.openmusic.domain.album.dto.response.AlbumResponse
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface AlbumService {
    fun getAlbums(pageable: Pageable): Page<AlbumResponse>
    fun searchAlbum(query: String, pageable: Pageable): Page<AlbumResponse>
    fun getAlbum(albumId: Long): AlbumResponse
    fun createAlbum(request: AlbumCreateRequest)
    fun updateAlbum(albumId: Long, request: AlbumUpdateRequest)
    fun deleteAlbum(albumId: Long)
    fun addAlbumLike(albumId: Long)
    fun removeAlbumLike(albumId: Long)
}
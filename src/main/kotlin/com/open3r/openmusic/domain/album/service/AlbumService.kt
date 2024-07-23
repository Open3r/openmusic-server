package com.open3r.openmusic.domain.album.service

import com.open3r.openmusic.domain.album.dto.request.AlbumCreateRequest
import com.open3r.openmusic.domain.album.dto.request.AlbumUpdateRequest
import com.open3r.openmusic.domain.album.dto.response.AlbumResponse

interface AlbumService {
    fun getAlbums(): List<AlbumResponse>
    fun getAlbum(albumId: Long): AlbumResponse
    fun searchAlbum(query: String): List<AlbumResponse>
    fun createAlbum(request: AlbumCreateRequest)
    fun updateAlbum(albumId: Long, request: AlbumUpdateRequest)
    fun deleteAlbum(albumId: Long)
    fun addAlbumLike(albumId: Long)
    fun removeAlbumLike(albumId: Long)
}
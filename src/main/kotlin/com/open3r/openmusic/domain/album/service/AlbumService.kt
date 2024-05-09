package com.open3r.openmusic.domain.album.service

import com.open3r.openmusic.domain.album.dto.request.AlbumCreateRequest
import com.open3r.openmusic.domain.album.dto.response.AlbumResponse

interface AlbumService {
    fun getAlbums(): List<AlbumResponse>
    fun getAlbum(albumId: Long): AlbumResponse
    fun createAlbum(request: AlbumCreateRequest): AlbumResponse
    fun deleteAlbum(albumId: Long): AlbumResponse
}
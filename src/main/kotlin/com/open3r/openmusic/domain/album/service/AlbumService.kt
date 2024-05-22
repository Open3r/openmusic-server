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
    fun createAlbumSong(albumId: Long, songId: Long)
    fun deleteAlbumSong(albumId: Long, songId: Long)
    fun createAlbumLike(albumId: Long)
    fun deleteAlbumLike(albumId: Long)
}
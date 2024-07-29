package com.open3r.openmusic.domain.playlist.service

import com.open3r.openmusic.domain.playlist.dto.request.PlaylistCreateRequest
import com.open3r.openmusic.domain.playlist.dto.request.PlaylistUpdateRequest
import com.open3r.openmusic.domain.playlist.dto.response.PlaylistResponse
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice

interface PlaylistService {
    fun getPlaylists(pageable: Pageable): Page<PlaylistResponse>
    fun getPlaylist(playlistId: Long): PlaylistResponse
    fun searchPlaylists(query: String, pageable: Pageable): Slice<PlaylistResponse>
    fun createPlaylist(request: PlaylistCreateRequest): PlaylistResponse
    fun updatePlaylist(playlistId: Long, request: PlaylistUpdateRequest)
    fun deletePlaylist(playlistId: Long)
    fun addSongToPlaylist(playlistId: Long, songId: Long): PlaylistResponse
    fun removeSongFromPlaylist(playlistId: Long, songId: Long): PlaylistResponse
    fun addLikeToPlaylist(playlistId: Long): PlaylistResponse
    fun removeLikeFromPlaylist(playlistId: Long): PlaylistResponse
}
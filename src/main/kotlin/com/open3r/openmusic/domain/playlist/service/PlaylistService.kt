package com.open3r.openmusic.domain.playlist.service

import com.open3r.openmusic.domain.playlist.dto.request.PlaylistCreateRequest
import com.open3r.openmusic.domain.playlist.dto.request.PlaylistUpdateRequest
import com.open3r.openmusic.domain.playlist.dto.response.PlaylistResponse

interface PlaylistService {
    fun getPlaylists(): List<PlaylistResponse>
    fun getPlaylist(playlistId: Long): PlaylistResponse
    fun createPlaylist(request: PlaylistCreateRequest): PlaylistResponse
    fun updatePlaylist(playlistId: Long, request: PlaylistUpdateRequest): PlaylistResponse
    fun deletePlaylist(playlistId: Long): PlaylistResponse
}
package com.open3r.openmusic.domain.playlist.service

import com.open3r.openmusic.domain.playlist.dto.request.PlaylistAddSongRequest
import com.open3r.openmusic.domain.playlist.dto.request.PlaylistCreateRequest
import com.open3r.openmusic.domain.playlist.dto.request.PlaylistRemoveSongRequest
import com.open3r.openmusic.domain.playlist.dto.request.PlaylistUpdateRequest
import com.open3r.openmusic.domain.playlist.dto.response.PlaylistResponse
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface PlaylistService {
    fun getPlaylists(pageable: Pageable): Page<PlaylistResponse>
    fun getPlaylist(playlistId: Long): PlaylistResponse
    fun searchPlaylist(query: String): List<PlaylistResponse>
    fun createPlaylist(request: PlaylistCreateRequest): PlaylistResponse
    fun updatePlaylist(playlistId: Long, request: PlaylistUpdateRequest)
    fun deletePlaylist(playlistId: Long)
    fun addSongToPlaylist(playlistId: Long, request: PlaylistAddSongRequest)
    fun removeSongToPlaylist(playlistId: Long, request: PlaylistRemoveSongRequest)
    fun addLikeToPlaylist(playlistId: Long)
    fun removeLikeToPlaylist(playlistId: Long)
}
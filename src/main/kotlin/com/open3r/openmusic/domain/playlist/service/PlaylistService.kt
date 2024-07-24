package com.open3r.openmusic.domain.playlist.service

import com.open3r.openmusic.domain.playlist.dto.request.PlaylistCreateRequest
import com.open3r.openmusic.domain.playlist.dto.request.PlaylistUpdateRequest
import com.open3r.openmusic.domain.playlist.dto.response.PlaylistResponse

interface PlaylistService {
    fun getPlaylists(): List<PlaylistResponse>
    fun getPublicPlaylists(): List<PlaylistResponse>
    fun getPrivatePlaylists(): List<PlaylistResponse>
    fun getMyPlaylists(): List<PlaylistResponse>
    fun getPlaylist(playlistId: Long): PlaylistResponse
    fun searchPlaylist(query: String): List<PlaylistResponse>
    fun createPlaylist(request: PlaylistCreateRequest)
    fun updatePlaylist(playlistId: Long, request: PlaylistUpdateRequest)
    fun deletePlaylist(playlistId: Long)
    fun createPlaylistSong(playlistId: Long, songId: Long)
    fun deletePlaylistSong(playlistId: Long, songId: Long)
    fun createPlaylistLike(playlistId: Long)
    fun deletePlaylistLike(playlistId: Long)
}
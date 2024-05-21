package com.open3r.openmusic.domain.playlist.service

import com.open3r.openmusic.domain.playlist.dto.request.PlaylistCreateRequest
import com.open3r.openmusic.domain.playlist.dto.request.PlaylistSongCreateRequest
import com.open3r.openmusic.domain.playlist.dto.request.PlaylistSongDeleteRequest
import com.open3r.openmusic.domain.playlist.dto.response.PlaylistResponse

interface PlaylistService {
    fun getPlaylists(): List<PlaylistResponse>
    fun getPlaylist(playlistId: Long): PlaylistResponse
    fun createPlaylist(request: PlaylistCreateRequest): PlaylistResponse
    fun deletePlaylist(playlistId: Long)
    fun createPlaylistSong(playlistId: Long, request: PlaylistSongCreateRequest)
    fun deletePlaylistSong(playlistId: Long, request: PlaylistSongDeleteRequest)
}
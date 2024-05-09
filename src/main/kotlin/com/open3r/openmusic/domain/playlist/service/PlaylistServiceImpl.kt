package com.open3r.openmusic.domain.playlist.service

import com.open3r.openmusic.domain.playlist.domain.Playlist
import com.open3r.openmusic.domain.playlist.dto.request.PlaylistCreateRequest
import com.open3r.openmusic.domain.playlist.dto.request.PlaylistUpdateRequest
import com.open3r.openmusic.domain.playlist.dto.response.PlaylistResponse
import com.open3r.openmusic.domain.playlist.repository.PlaylistRepository
import com.open3r.openmusic.global.error.CustomException
import com.open3r.openmusic.global.error.ErrorCode
import com.open3r.openmusic.global.security.UserSecurity
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PlaylistServiceImpl(
    private val playlistRepository: PlaylistRepository,
    private val userSecurity: UserSecurity
) : PlaylistService {
    @Transactional(readOnly = true)
    override fun getPlaylists(): List<PlaylistResponse> {
        val playlists = playlistRepository.findAll()

        return playlists.map { PlaylistResponse.of(it) }
    }

    @Transactional(readOnly = true)
    override fun getPlaylist(playlistId: Long): PlaylistResponse {
        val playlist =
            playlistRepository.findByIdOrNull(playlistId) ?: throw CustomException(ErrorCode.PLAYLIST_NOT_FOUND)

        return PlaylistResponse.of(playlist)
    }

    @Transactional
    override fun createPlaylist(request: PlaylistCreateRequest): PlaylistResponse {
        val user = userSecurity.user
        var playlist = Playlist(
            title = request.title,
            description = request.description,
            user = user
        )

        playlist = playlistRepository.save(playlist)

        return PlaylistResponse.of(playlist)
    }

    @Transactional
    override fun updatePlaylist(playlistId: Long, request: PlaylistUpdateRequest): PlaylistResponse {
        val playlist =
            playlistRepository.findByIdOrNull(playlistId) ?: throw CustomException(ErrorCode.PLAYLIST_NOT_FOUND)

        playlist.title = request.title ?: playlist.title
        playlist.description = request.description ?: playlist.description

        playlistRepository.save(playlist)

        return PlaylistResponse.of(playlist)
    }

    @Transactional
    override fun deletePlaylist(playlistId: Long): PlaylistResponse {
        val playlist =
            playlistRepository.findByIdOrNull(playlistId) ?: throw CustomException(ErrorCode.PLAYLIST_NOT_FOUND)

        playlistRepository.delete(playlist)

        return PlaylistResponse.of(playlist)
    }
}
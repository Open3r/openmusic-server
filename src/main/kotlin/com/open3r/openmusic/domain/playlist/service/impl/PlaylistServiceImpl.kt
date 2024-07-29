package com.open3r.openmusic.domain.playlist.service.impl

import com.open3r.openmusic.domain.playlist.domain.entity.PlaylistEntity
import com.open3r.openmusic.domain.playlist.domain.entity.PlaylistSongEntity
import com.open3r.openmusic.domain.playlist.dto.request.PlaylistCreateRequest
import com.open3r.openmusic.domain.playlist.dto.request.PlaylistUpdateRequest
import com.open3r.openmusic.domain.playlist.dto.response.PlaylistResponse
import com.open3r.openmusic.domain.playlist.repository.PlaylistQueryRepository
import com.open3r.openmusic.domain.playlist.repository.PlaylistRepository
import com.open3r.openmusic.domain.playlist.service.PlaylistService
import com.open3r.openmusic.domain.song.repository.SongRepository
import com.open3r.openmusic.domain.user.domain.enums.UserRole
import com.open3r.openmusic.global.error.CustomException
import com.open3r.openmusic.global.error.ErrorCode
import com.open3r.openmusic.global.security.UserSecurity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PlaylistServiceImpl(
    private val playlistQueryRepository: PlaylistQueryRepository,
    private val playlistRepository: PlaylistRepository,
    private val userSecurity: UserSecurity,
    private val songRepository: SongRepository
) : PlaylistService {
    @Transactional(readOnly = true)
    override fun getPlaylists(pageable: Pageable): Page<PlaylistResponse> {
        return playlistQueryRepository.getPlaylists(pageable).map { it.toResponse() }
    }

    @Transactional(readOnly = true)
    override fun getPlaylist(playlistId: Long): PlaylistResponse {
        val playlist =
            playlistRepository.findByIdOrNull(playlistId) ?: throw CustomException(ErrorCode.PLAYLIST_NOT_FOUND)

        return playlist.toResponse()
    }

    @Transactional(readOnly = true)
    override fun searchPlaylist(query: String): List<PlaylistResponse> {
        return playlistRepository.findAllByTitleContainingIgnoreCase(query).map { it.toResponse() }
    }

    @Transactional
    override fun createPlaylist(request: PlaylistCreateRequest): PlaylistResponse {
        val user = userSecurity.user
        val playlist = request.toEntity(user)

        return PlaylistResponse.of(playlistRepository.save(playlist), user)
    }

    @Transactional
    override fun updatePlaylist(playlistId: Long, request: PlaylistUpdateRequest) {
        val playlist =
            playlistRepository.findByIdOrNull(playlistId) ?: throw CustomException(ErrorCode.PLAYLIST_NOT_FOUND)
        val user = userSecurity.user

        if (playlist.artist.id != user.id) throw CustomException(ErrorCode.PLAYLIST_NOT_UPDATABLE)

        playlist.title = request.title ?: playlist.title
        playlist.coverUrl = request.coverUrl ?: playlist.coverUrl
        playlist.scope = request.scope ?: playlist.scope

        playlistRepository.save(playlist)
    }

    @Transactional
    override fun deletePlaylist(playlistId: Long) {
        val playlist =
            playlistRepository.findByIdOrNull(playlistId) ?: throw CustomException(ErrorCode.PLAYLIST_NOT_FOUND)
        val user = userSecurity.user

        if (playlist.artist.id != user.id && user.role != UserRole.ADMIN) throw CustomException(ErrorCode.PLAYLIST_NOT_DELETABLE)

        playlistRepository.delete(playlist)
    }

    @Transactional
    override fun addSongToPlaylist(playlistId: Long, songId: Long): PlaylistResponse {
        val playlist =
            playlistRepository.findByIdOrNull(playlistId) ?: throw CustomException(ErrorCode.PLAYLIST_NOT_FOUND)
        val user = userSecurity.user

        if (playlist.artist.id != user.id) throw CustomException(ErrorCode.PLAYLIST_NOT_UPDATABLE)
        if (playlist.songs.any { it.id == songId }) throw CustomException(ErrorCode.PLAYLIST_SONG_ALREADY_EXISTS)

        val song = songRepository.findByIdOrNull(songId) ?: throw CustomException(ErrorCode.SONG_NOT_FOUND)

        playlist.songs.add(PlaylistSongEntity(playlist = playlist, song = song))

        return playlistRepository.save(playlist).toResponse()
    }

    @Transactional
    override fun removeSongFromPlaylist(playlistId: Long, songId: Long): PlaylistResponse {
        val playlist =
            playlistRepository.findByIdOrNull(playlistId) ?: throw CustomException(ErrorCode.PLAYLIST_NOT_FOUND)
        val user = userSecurity.user

        if (playlist.artist.id != user.id) throw CustomException(ErrorCode.PLAYLIST_NOT_UPDATABLE)
        if (playlist.songs.none { it.id == songId }) throw CustomException(ErrorCode.PLAYLIST_SONG_NOT_FOUND)

        val song = songRepository.findByIdOrNull(songId) ?: throw CustomException(ErrorCode.SONG_NOT_FOUND)

        playlist.songs.removeIf { it.song.id == song.id }

        return playlistRepository.save(playlist).toResponse()
    }

    @Transactional
    override fun addLikeToPlaylist(playlistId: Long): PlaylistResponse {
        val playlist =
            playlistRepository.findByIdOrNull(playlistId) ?: throw CustomException(ErrorCode.PLAYLIST_NOT_FOUND)
        val user = userSecurity.user

        if (playlist.likes.any { it.id == user.id }) throw CustomException(ErrorCode.PLAYLIST_LIKE_ALREADY_EXISTS)

        playlist.likes.add(user)

        return playlistRepository.save(playlist).toResponse()
    }

    @Transactional
    override fun removeLikeFromPlaylist(playlistId: Long): PlaylistResponse {
        val playlist =
            playlistRepository.findByIdOrNull(playlistId) ?: throw CustomException(ErrorCode.PLAYLIST_NOT_FOUND)
        val user = userSecurity.user

        if (playlist.likes.none { it.id == user.id }) throw CustomException(ErrorCode.PLAYLIST_LIKE_NOT_FOUND)

        playlist.likes.removeIf { it.id == user.id }

        return playlistRepository.save(playlist).toResponse()
    }

    private fun PlaylistEntity.toResponse() = if (userSecurity.isAuthenticated) {
        PlaylistResponse.of(this, userSecurity.user)
    } else {
        PlaylistResponse.of(this)
    }
}
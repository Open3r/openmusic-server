package com.open3r.openmusic.domain.playlist.service

import com.open3r.openmusic.domain.playlist.dto.request.PlaylistCreateRequest
import com.open3r.openmusic.domain.playlist.dto.response.PlaylistResponse
import com.open3r.openmusic.domain.playlist.repository.PlaylistRepository
import com.open3r.openmusic.domain.song.repository.SongRepository
import com.open3r.openmusic.domain.user.domain.UserRole
import com.open3r.openmusic.global.error.CustomException
import com.open3r.openmusic.global.error.ErrorCode
import com.open3r.openmusic.global.security.UserSecurity
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PlaylistServiceImpl(
    private val playlistRepository: PlaylistRepository,
    private val userSecurity: UserSecurity,
    private val songRepository: SongRepository
) : PlaylistService {
    @Transactional(readOnly = true)
    override fun getPlaylists(): List<PlaylistResponse> {
        return playlistRepository.findAll().map { PlaylistResponse.of(it) }
    }

    @Transactional(readOnly = true)
    override fun getPlaylist(playlistId: Long): PlaylistResponse {
        val playlist =
            playlistRepository.findByIdOrNull(playlistId) ?: throw CustomException(ErrorCode.PLAYLIST_NOT_FOUND)

        return PlaylistResponse.of(playlist)
    }

    @Transactional(readOnly = true)
    override fun searchPlaylist(query: String): List<PlaylistResponse> {
        val playlists = playlistRepository.findAllByTitleContainingIgnoreCase(query)

        return playlists.map { PlaylistResponse.of(it) }
    }

    @Transactional
    override fun createPlaylist(request: PlaylistCreateRequest) {
        val user = userSecurity.user
        val playlist = request.toEntity(user)

        playlistRepository.save(playlist)
    }

    @Transactional
    override fun deletePlaylist(playlistId: Long) {
        val playlist =
            playlistRepository.findByIdOrNull(playlistId) ?: throw CustomException(ErrorCode.PLAYLIST_NOT_FOUND)
        val user = userSecurity.user

        if (playlist.user.id != user.id && user.role != UserRole.ADMIN) throw CustomException(ErrorCode.PLAYLIST_NOT_DELETABLE)

        playlistRepository.delete(playlist)
    }

    @Transactional
    override fun createPlaylistSong(playlistId: Long, songId: Long) {
        val playlist =
            playlistRepository.findByIdOrNull(playlistId) ?: throw CustomException(ErrorCode.PLAYLIST_NOT_FOUND)
        val user = userSecurity.user

        if (playlist.user.id != user.id) throw CustomException(ErrorCode.PLAYLIST_NOT_UPDATABLE)

        if (playlist.songs.any { it.id == songId }) throw CustomException(ErrorCode.PLAYLIST_SONG_ALREADY_EXISTS)

        val song = songRepository.findByIdOrNull(songId) ?: throw CustomException(ErrorCode.SONG_NOT_FOUND)

        playlist.songs.add(song)

        playlistRepository.save(playlist)
    }

    @Transactional
    override fun deletePlaylistSong(playlistId: Long, songId: Long) {
        val playlist =
            playlistRepository.findByIdOrNull(playlistId) ?: throw CustomException(ErrorCode.PLAYLIST_NOT_FOUND)
        val user = userSecurity.user

        if (playlist.user.id != user.id) throw CustomException(ErrorCode.PLAYLIST_NOT_UPDATABLE)

        if (playlist.songs.none { it.id == songId }) throw CustomException(ErrorCode.PLAYLIST_SONG_NOT_FOUND)

        val song = songRepository.findByIdOrNull(songId) ?: throw CustomException(ErrorCode.SONG_NOT_FOUND)

        playlist.songs.removeIf { it.id == song.id }

        playlistRepository.save(playlist)
    }

    @Transactional
    override fun createPlaylistLike(playlistId: Long) {
        val playlist =
            playlistRepository.findByIdOrNull(playlistId) ?: throw CustomException(ErrorCode.PLAYLIST_NOT_FOUND)
        val user = userSecurity.user

        if (playlist.likes.any { it.id == user.id }) throw CustomException(ErrorCode.PLAYLIST_LIKE_ALREADY_EXISTS)

        playlist.likes.add(user)

        playlistRepository.save(playlist)
    }

    @Transactional
    override fun deletePlaylistLike(playlistId: Long) {
        val playlist =
            playlistRepository.findByIdOrNull(playlistId) ?: throw CustomException(ErrorCode.PLAYLIST_NOT_FOUND)
        val user = userSecurity.user

        if (playlist.likes.none { it.id == user.id }) throw CustomException(ErrorCode.PLAYLIST_LIKE_NOT_FOUND)

        playlist.likes.removeIf { it.id == user.id }

        playlistRepository.save(playlist)
    }
}
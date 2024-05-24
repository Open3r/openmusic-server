package com.open3r.openmusic.domain.song.service

import com.open3r.openmusic.domain.album.repository.AlbumRepository
import com.open3r.openmusic.domain.song.dto.request.SongCreateRequest
import com.open3r.openmusic.domain.song.dto.response.SongResponse
import com.open3r.openmusic.domain.song.repository.SongRepository
import com.open3r.openmusic.domain.user.domain.UserRole
import com.open3r.openmusic.global.error.CustomException
import com.open3r.openmusic.global.error.ErrorCode
import com.open3r.openmusic.global.security.UserSecurity
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SongServiceImpl(
    private val songRepository: SongRepository,
    private val albumRepository: AlbumRepository,
    private val userSecurity: UserSecurity,
) : SongService {
    @Transactional(readOnly = true)
    override fun getSongs(): List<SongResponse> {
        val songs = songRepository.findAll()

        return songs.map { SongResponse.of(it) }
    }

    @Transactional(readOnly = true)
    override fun getSong(songId: Long): SongResponse {
        val song = songRepository.findByIdOrNull(songId) ?: throw CustomException(ErrorCode.SONG_NOT_FOUND)

        return SongResponse.of(song)
    }

    @Transactional(readOnly = true)
    override fun searchSong(query: String): List<SongResponse> {
        val songs = songRepository.findAllByTitleContainingIgnoreCase(query)

        return songs.map { SongResponse.of(it) }
    }

    @Transactional
    override fun createSong(request: SongCreateRequest) {
        val album = albumRepository.findByIdOrNull(request.albumId) ?: throw CustomException(ErrorCode.ALBUM_NOT_FOUND)
        val artist = userSecurity.user
        val song = request.toEntity(album, artist)

        songRepository.save(song)
    }

    @Transactional
    override fun deleteSong(songId: Long) {
        val song = songRepository.findByIdOrNull(songId) ?: throw CustomException(ErrorCode.SONG_NOT_FOUND)
        val user = userSecurity.user

        if (song.artist.id != user.id && user.role != UserRole.ADMIN) throw CustomException(ErrorCode.SONG_NOT_DELETABLE)

        songRepository.delete(song)
    }

    @Transactional
    override fun createSongLike(songId: Long) {
        val song = songRepository.findByIdOrNull(songId) ?: throw CustomException(ErrorCode.SONG_NOT_FOUND)
        val user = userSecurity.user

        if (song.likes.any { it.id == user.id }) throw CustomException(ErrorCode.SONG_LIKE_ALREADY_EXISTS)

        song.likes.add(user)

        songRepository.save(song)
    }

    @Transactional
    override fun deleteSongLike(songId: Long) {
        val song = songRepository.findByIdOrNull(songId) ?: throw CustomException(ErrorCode.SONG_NOT_FOUND)
        val user = userSecurity.user

        if (song.likes.none { it.id == user.id }) throw CustomException(ErrorCode.SONG_LIKE_NOT_FOUND)

        song.likes.removeIf { it.id == user.id }

        songRepository.save(song)
    }
}
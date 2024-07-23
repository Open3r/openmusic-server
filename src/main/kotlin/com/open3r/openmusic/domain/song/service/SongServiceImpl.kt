package com.open3r.openmusic.domain.song.service

import com.open3r.openmusic.domain.album.repository.AlbumRepository
import com.open3r.openmusic.domain.song.dto.request.SongCreateRequest
import com.open3r.openmusic.domain.song.dto.request.SongUpdateRequest
import com.open3r.openmusic.domain.song.dto.response.Song
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
    override fun getSongs(): List<Song> {
        val songs = songRepository.findAll()

        return songs.map { Song.of(it) }
    }

    @Transactional(readOnly = true)
    override fun getSong(songId: Long): Song {
        val song = songRepository.findByIdOrNull(songId) ?: throw CustomException(ErrorCode.SONG_NOT_FOUND)

        return Song.of(song)
    }

    @Transactional(readOnly = true)
    override fun searchSong(query: String): List<Song> {
        val songs = songRepository.findAllByTitleContainingIgnoreCase(query)

        return songs.map { Song.of(it) }
    }

    @Transactional
    override fun updateSong(songId: Long, request: SongUpdateRequest) {
        val song = songRepository.findByIdOrNull(songId) ?: throw CustomException(ErrorCode.SONG_NOT_FOUND)
        val user = userSecurity.user

        if (song.artist.id != user.id && user.role != UserRole.ADMIN) throw CustomException(ErrorCode.SONG_NOT_UPDATABLE)

        song.title = request.title ?: song.title
        song.url = request.url ?: song.url

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
    override fun addSongLike(songId: Long) {
        val song = songRepository.findByIdOrNull(songId) ?: throw CustomException(ErrorCode.SONG_NOT_FOUND)
        val user = userSecurity.user

        if (song.likes.any { it.id == user.id }) throw CustomException(ErrorCode.SONG_LIKE_ALREADY_EXISTS)

        song.likes.add(user)

        songRepository.save(song)
    }

    @Transactional
    override fun removeSongLike(songId: Long) {
        val song = songRepository.findByIdOrNull(songId) ?: throw CustomException(ErrorCode.SONG_NOT_FOUND)
        val user = userSecurity.user

        if (song.likes.none { it.id == user.id }) throw CustomException(ErrorCode.SONG_LIKE_NOT_FOUND)

        song.likes.removeIf { it.id == user.id }

        songRepository.save(song)
    }
}
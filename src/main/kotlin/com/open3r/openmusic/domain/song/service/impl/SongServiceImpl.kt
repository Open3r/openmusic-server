package com.open3r.openmusic.domain.song.service.impl

import com.open3r.openmusic.domain.song.domain.entity.SongEntity
import com.open3r.openmusic.domain.song.domain.enums.SongGenre
import com.open3r.openmusic.domain.song.dto.request.SongUpdateRequest
import com.open3r.openmusic.domain.song.dto.response.SongResponse
import com.open3r.openmusic.domain.song.repository.SongQueryRepository
import com.open3r.openmusic.domain.song.repository.SongRepository
import com.open3r.openmusic.domain.song.service.SongService
import com.open3r.openmusic.domain.user.domain.enums.UserRole
import com.open3r.openmusic.global.error.CustomException
import com.open3r.openmusic.global.error.ErrorCode
import com.open3r.openmusic.global.security.UserSecurity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SongServiceImpl(
    private val songRepository: SongRepository,
    private val userSecurity: UserSecurity,
    private val songQueryRepository: SongQueryRepository,
) : SongService {
    @Transactional(readOnly = true)
    override fun getSongs(pageable: Pageable): Page<SongResponse> {
        return songQueryRepository.getSongs(pageable).map { it.toResponse() }
    }

    @Transactional(readOnly = true)
    override fun getRankingSongs(pageable: Pageable): Slice<SongResponse> {
        return songQueryRepository.getRankingSongs(pageable).map { it.toResponse() }
    }

    @Transactional(readOnly = true)
    override fun getGenreSongs(genre: SongGenre, pageable: Pageable): Page<SongResponse> {
        return songQueryRepository.getGenreSongs(genre, pageable).map { it.toResponse() }
    }

    @Transactional(readOnly = true)
    override fun getMySongs(): List<SongResponse> {
        val user = userSecurity.user
        val songs = songRepository.findAllByArtist(user)

        return songs.map { SongResponse.of(it, user) }
    }

    @Transactional(readOnly = true)
    override fun getSong(songId: Long): SongResponse {
        val song = songRepository.findByIdOrNull(songId) ?: throw CustomException(ErrorCode.SONG_NOT_FOUND)

        return song.toResponse()
    }

    @Transactional(readOnly = true)
    override fun searchSong(query: String): List<SongResponse> {
        return songRepository.findAllByTitleContainingIgnoreCase(query).map { it.toResponse() }
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
    override fun addLikeToSong(songId: Long): SongResponse {
        val song = songRepository.findByIdOrNull(songId) ?: throw CustomException(ErrorCode.SONG_NOT_FOUND)
        val user = userSecurity.user

        if (song.likes.any { it.id == user.id }) throw CustomException(ErrorCode.SONG_LIKE_ALREADY_EXISTS)

        song.likes.add(user)

        return songRepository.save(song).toResponse()
    }

    @Transactional
    override fun removeLikeFromSAlbum(songId: Long): SongResponse {
        val song = songRepository.findByIdOrNull(songId) ?: throw CustomException(ErrorCode.SONG_NOT_FOUND)
        val user = userSecurity.user

        if (song.likes.none { it.id == user.id }) throw CustomException(ErrorCode.SONG_LIKE_NOT_FOUND)

        song.likes.removeIf { it.id == user.id }

        return songRepository.save(song).toResponse()
    }

    private fun SongEntity.toResponse() = if (userSecurity.isAuthenticated) {
        SongResponse.of(this, userSecurity.user)
    } else {
        SongResponse.of(this)
    }
}
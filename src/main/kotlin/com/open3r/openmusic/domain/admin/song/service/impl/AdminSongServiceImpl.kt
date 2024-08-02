package com.open3r.openmusic.domain.admin.song.service.impl

import com.open3r.openmusic.domain.admin.song.repository.AdminSongQueryRepository
import com.open3r.openmusic.domain.admin.song.repository.impl.AdminSongQueryRepositoryImpl
import com.open3r.openmusic.domain.admin.song.service.AdminSongService
import com.open3r.openmusic.domain.song.dto.response.SongResponse
import com.open3r.openmusic.domain.song.repository.SongRepository
import com.open3r.openmusic.global.error.CustomException
import com.open3r.openmusic.global.error.ErrorCode
import com.open3r.openmusic.global.security.UserSecurity
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AdminSongServiceImpl(
    private val userSecurity: UserSecurity,
    private val songRepository: SongRepository,
    private val adminSongQueryRepository: AdminSongQueryRepository
) : AdminSongService {
    @Transactional(readOnly = true)
    override fun getSongs() = songRepository.findAll().map { SongResponse.of(it, userSecurity.user) }

    @Transactional(readOnly = true)
    override fun getPendingSongs() = adminSongQueryRepository.getPendingSongs().map { SongResponse.of(it) }

    @Transactional(readOnly = true)
    override fun getApprovedSongs() = adminSongQueryRepository.getApprovedSongs().map { SongResponse.of(it) }

    @Transactional(readOnly = true)
    override fun getRejectedSongs() = adminSongQueryRepository.getRejectedSongs().map { SongResponse.of(it) }

    @Transactional(readOnly = true)
    override fun getDeletedSongs() = adminSongQueryRepository.getDeletedSongs().map { SongResponse.of(it) }

    @Transactional
    override fun approveSong(songId: Long) {
        val song = songRepository.findByIdOrNull(songId) ?: throw CustomException(ErrorCode.SONG_NOT_FOUND)

        song.approve()

        songRepository.save(song)
    }

    @Transactional
    override fun rejectSong(songId: Long) {
        val song = songRepository.findByIdOrNull(songId) ?: throw CustomException(ErrorCode.SONG_NOT_FOUND)

        song.reject()

        songRepository.save(song)
    }

    @Transactional
    override fun deleteSong(songId: Long) {
        val song = songRepository.findById(songId).orElseThrow { throw CustomException(ErrorCode.SONG_NOT_FOUND) }

        songRepository.delete(song)
    }
}
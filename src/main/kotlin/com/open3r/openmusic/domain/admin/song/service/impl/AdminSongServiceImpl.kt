package com.open3r.openmusic.domain.admin.song.service.impl

import com.open3r.openmusic.domain.admin.song.service.AdminSongService
import com.open3r.openmusic.domain.song.repository.SongRepository
import com.open3r.openmusic.global.error.CustomException
import com.open3r.openmusic.global.error.ErrorCode
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AdminSongServiceImpl(
    private val songRepository: SongRepository
) : AdminSongService {
    @Transactional
    override fun deleteSong(songId: Long) {
        val song = songRepository.findById(songId).orElseThrow { throw CustomException(ErrorCode.SONG_NOT_FOUND) }

        songRepository.delete(song)
    }
}
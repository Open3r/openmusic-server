package com.open3r.openmusic.domain.song.service

import com.open3r.openmusic.domain.song.domain.Song
import com.open3r.openmusic.domain.song.dto.request.SongCreateRequest
import com.open3r.openmusic.domain.song.dto.response.SongResponse
import com.open3r.openmusic.domain.song.repository.SongRepository
import com.open3r.openmusic.global.error.CustomException
import com.open3r.openmusic.global.error.ErrorCode
import com.open3r.openmusic.global.security.UserSecurity
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SongServiceImpl(
    private val songRepository: SongRepository,
    private val userSecurity: UserSecurity
): SongService {
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

    @Transactional
    override fun createSong(request: SongCreateRequest): SongResponse {
        val user = userSecurity.user

        val song = Song(
            title = request.title,
            description = request.description,
            user = user
        )

        songRepository.save(song)

        return SongResponse.of(song)
    }

    @Transactional
    override fun deleteSong(songId: Long): SongResponse {
        val song = songRepository.findByIdOrNull(songId) ?: throw CustomException(ErrorCode.SONG_NOT_FOUND)

        songRepository.delete(song)

        return SongResponse.of(song)
    }
}

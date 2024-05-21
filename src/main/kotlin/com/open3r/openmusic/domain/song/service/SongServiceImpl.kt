package com.open3r.openmusic.domain.song.service

import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.ObjectMetadata
import com.open3r.openmusic.domain.song.dto.request.SongCreateRequest
import com.open3r.openmusic.domain.song.dto.response.SongResponse
import com.open3r.openmusic.domain.song.dto.response.SongUploadResponse
import com.open3r.openmusic.domain.song.repository.SongRepository
import com.open3r.openmusic.global.error.CustomException
import com.open3r.openmusic.global.error.ErrorCode
import com.open3r.openmusic.global.security.UserSecurity
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import java.util.*

@Service
class SongServiceImpl(
    private val songRepository: SongRepository,
    private val userSecurity: UserSecurity,
    private val amazonS3Client: AmazonS3Client
) : SongService {
    @Value("\${cloud.aws.s3.bucket}")
    private lateinit var bucket: String

    @Value("\${cloud.aws.region.static}")
    private lateinit var region: String

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
    override fun createSong(request: SongCreateRequest): SongResponse {
        val user = userSecurity.user
        val song = songRepository.save(request.toEntity(user))

        return SongResponse.of(song)
    }

    @Transactional
    override fun deleteSong(songId: Long) {
        val song = songRepository.findByIdOrNull(songId) ?: throw CustomException(ErrorCode.SONG_NOT_FOUND)

        songRepository.delete(song)
    }

    @Transactional
    override fun uploadSong(file: MultipartFile): SongUploadResponse {
        if (file.isEmpty) throw CustomException(ErrorCode.SONG_EMPTY)

        val name = "${UUID.randomUUID()}_${file.originalFilename}"
        val metadata = ObjectMetadata().apply {
            contentType = file.contentType
            contentLength = file.size
        }

        amazonS3Client.putObject(bucket, name, file.inputStream, metadata)

        val url = "https://$bucket.s3.$region.amazonaws.com/$name"

        return SongUploadResponse(url = url)
    }

}
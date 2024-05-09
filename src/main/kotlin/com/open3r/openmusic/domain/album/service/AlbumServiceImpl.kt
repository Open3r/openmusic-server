package com.open3r.openmusic.domain.album.service

import com.open3r.openmusic.domain.album.dto.request.AlbumCreateRequest
import com.open3r.openmusic.domain.album.dto.response.AlbumResponse
import com.open3r.openmusic.domain.album.repository.AlbumRepository
import com.open3r.openmusic.global.error.CustomException
import com.open3r.openmusic.global.error.ErrorCode
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AlbumServiceImpl(
    private val albumRepository: AlbumRepository,
): AlbumService {
    @Transactional(readOnly = true)
    override fun getAlbums(): List<AlbumResponse> {
        val albums = albumRepository.findAll()

        return albums.map { AlbumResponse.of(it) }
    }

    @Transactional(readOnly = true)
    override fun getAlbum(albumId: Long): AlbumResponse {
        val album = albumRepository.findByIdOrNull(albumId) ?: throw CustomException(ErrorCode.ALBUM_NOT_FOUND)

        return AlbumResponse.of(album)
    }

    @Transactional
    override fun createAlbum(request: AlbumCreateRequest): AlbumResponse {
        val album = albumRepository.save(request.toEntity())

        return AlbumResponse.of(album)
    }

    @Transactional
    override fun deleteAlbum(albumId: Long): AlbumResponse {
        val album = albumRepository.findByIdOrNull(albumId) ?: throw CustomException(ErrorCode.ALBUM_NOT_FOUND)

        albumRepository.delete(album)

        return AlbumResponse.of(album)
    }
}
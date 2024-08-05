package com.open3r.openmusic.domain.admin.album.service.impl

import com.open3r.openmusic.domain.admin.album.repository.AdminAlbumQueryRepository
import com.open3r.openmusic.domain.admin.album.service.AdminAlbumService
import com.open3r.openmusic.domain.album.domain.enums.AlbumStatus
import com.open3r.openmusic.domain.album.dto.response.AlbumResponse
import com.open3r.openmusic.domain.album.repository.AlbumRepository
import com.open3r.openmusic.global.error.CustomException
import com.open3r.openmusic.global.error.ErrorCode
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class AdminAlbumServiceImpl(
    private val albumRepository: AlbumRepository,
    private val adminAlbumQueryRepository: AdminAlbumQueryRepository
) : AdminAlbumService {
    override fun getPendingAlbums(pageable: Pageable): Page<AlbumResponse> {
        return adminAlbumQueryRepository.getPendingAlbums(pageable).map { AlbumResponse.of(it) }
    }

    override fun getApprovedAlbums(pageable: Pageable): Page<AlbumResponse> {
        return adminAlbumQueryRepository.getApprovedAlbums(pageable).map { AlbumResponse.of(it) }
    }

    override fun getRejectedAlbums(pageable: Pageable): Page<AlbumResponse> {
        return adminAlbumQueryRepository.getRejectedAlbums(pageable).map { AlbumResponse.of(it) }
    }

    override fun getDeletedAlbums(pageable: Pageable): Page<AlbumResponse> {
        return adminAlbumQueryRepository.getDeletedAlbums(pageable).map { AlbumResponse.of(it) }
    }

    override fun approveAlbum(albumId: Long) {
        val album = albumRepository.findByIdOrNull(albumId) ?: throw CustomException(ErrorCode.ALBUM_NOT_FOUND)

        if (album.status != AlbumStatus.PENDING) {
            throw CustomException(ErrorCode.ALBUM_STATUS_INVALID)
        }

        album.status = AlbumStatus.APPROVED

        album.songs.forEach { it.song.status = AlbumStatus.APPROVED }

        albumRepository.save(album)
    }

    override fun rejectAlbum(albumId: Long) {
        val album = albumRepository.findByIdOrNull(albumId) ?: throw CustomException(ErrorCode.ALBUM_NOT_FOUND)

        if (album.status != AlbumStatus.PENDING) {
            throw CustomException(ErrorCode.ALBUM_STATUS_INVALID)
        }

        album.status = AlbumStatus.REJECTED

        album.songs.forEach { it.song.status = AlbumStatus.REJECTED }

        albumRepository.save(album)
    }

    override fun deleteAlbum(albumId: Long) {
        val album = albumRepository.findByIdOrNull(albumId) ?: throw CustomException(ErrorCode.ALBUM_NOT_FOUND)

        album.status = AlbumStatus.DELETED

        album.songs.forEach { it.song.status = AlbumStatus.DELETED }

        albumRepository.save(album)
    }
}
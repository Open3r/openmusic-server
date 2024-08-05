package com.open3r.openmusic.domain.admin.album.service.impl

import com.open3r.openmusic.domain.admin.album.service.AdminAlbumService
import com.open3r.openmusic.domain.album.domain.enums.AlbumStatus
import com.open3r.openmusic.domain.album.repository.AlbumRepository
import com.open3r.openmusic.global.error.CustomException
import com.open3r.openmusic.global.error.ErrorCode
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class AdminAlbumServiceImpl(
    private val albumRepository: AlbumRepository,
) : AdminAlbumService {
    override fun deleteAlbum(albumId: Long) {
        val album = albumRepository.findByIdOrNull(albumId) ?: throw CustomException(ErrorCode.ALBUM_NOT_FOUND)

        album.status = AlbumStatus.DELETED

        album.songs.forEach { it.song.status = AlbumStatus.DELETED }

        albumRepository.save(album)
    }
}
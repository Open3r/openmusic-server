package com.open3r.openmusic.domain.album.service.impl

import com.open3r.openmusic.domain.album.domain.entity.AlbumEntity
import com.open3r.openmusic.domain.album.domain.entity.AlbumLikeEntity
import com.open3r.openmusic.domain.album.domain.entity.AlbumSongEntity
import com.open3r.openmusic.domain.album.dto.request.AlbumCreateRequest
import com.open3r.openmusic.domain.album.dto.request.AlbumUpdateRequest
import com.open3r.openmusic.domain.album.dto.response.AlbumResponse
import com.open3r.openmusic.domain.album.repository.AlbumQueryRepository
import com.open3r.openmusic.domain.album.repository.AlbumRepository
import com.open3r.openmusic.domain.album.service.AlbumService
import com.open3r.openmusic.domain.song.domain.entity.SongEntity
import com.open3r.openmusic.domain.song.repository.SongRepository
import com.open3r.openmusic.global.error.CustomException
import com.open3r.openmusic.global.error.ErrorCode
import com.open3r.openmusic.global.security.UserSecurity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AlbumServiceImpl(
    private val userSecurity: UserSecurity,
    private val songRepository: SongRepository,
    private val albumRepository: AlbumRepository,
    private val albumQueryRepository: AlbumQueryRepository
) : AlbumService {
    @Transactional(readOnly = true)
    override fun getAlbums(pageable: Pageable): Page<AlbumResponse> {
        return albumQueryRepository.getAlbums(pageable).map { it.toResponse() }
    }

    @Transactional(readOnly = true)
    override fun searchAlbums(query: String, pageable: Pageable): Page<AlbumResponse> {
        return albumQueryRepository.searchAlbums(query, pageable).map { it.toResponse() }
    }

    @Transactional(readOnly = true)
    override fun getAlbum(albumId: Long): AlbumResponse {
        val album = albumRepository.findByIdOrNull(albumId) ?: throw CustomException(ErrorCode.ALBUM_NOT_FOUND)

        return album.toResponse()
    }

    @Transactional
    override fun createAlbum(request: AlbumCreateRequest) {
        val user = userSecurity.user
        val album = albumRepository.save(
            AlbumEntity(
                title = request.title,
                description = request.description,
                coverUrl = request.coverUrl,
                artist = user,
                genre = request.genre,
                scope = request.scope,
            )
        )

        album.songs.addAll(songRepository.saveAll(request.songs.map {
            SongEntity(
                title = it.title,
                url = it.url,
                album = album,
                genre = album.genre,
                scope = album.scope,
                artist = user,
            )
        }).map { AlbumSongEntity(song = it, album = album) })

        albumRepository.save(album)
    }

    @Transactional
    override fun updateAlbum(albumId: Long, request: AlbumUpdateRequest) {
        val album = albumRepository.findByIdOrNull(albumId) ?: throw CustomException(ErrorCode.ALBUM_NOT_FOUND)
        val user = userSecurity.user

        if (album.artist.id != user.id) throw CustomException(ErrorCode.ALBUM_NOT_UPDATABLE)

        album.title = request.title ?: album.title
        album.description = request.description ?: album.description
        album.coverUrl = request.coverUrl ?: album.coverUrl
        album.genre = request.genre ?: album.genre
        album.scope = request.scope ?: album.scope

        albumRepository.save(album)
    }

    @Transactional
    override fun deleteAlbum(albumId: Long) {
        val album = albumRepository.findByIdOrNull(albumId) ?: throw CustomException(ErrorCode.ALBUM_NOT_FOUND)
        val user = userSecurity.user

        if (album.artist.id != user.id) throw CustomException(ErrorCode.ALBUM_NOT_DELETABLE)

        albumRepository.delete(album)
    }

    @Transactional
    override fun addLikeToAlbum(albumId: Long): AlbumResponse {
        val album = albumRepository.findByIdOrNull(albumId) ?: throw CustomException(ErrorCode.ALBUM_NOT_FOUND)
        val user = userSecurity.user

        if (album.likes.any { it.user.id == user.id }) throw CustomException(ErrorCode.ALBUM_LIKE_ALREADY_EXISTS)

        album.likes.add(AlbumLikeEntity(user = user, album = album))

        return albumRepository.save(album).toResponse()
    }

    @Transactional
    override fun removeLikeFromAlbum(albumId: Long): AlbumResponse {
        val album = albumRepository.findByIdOrNull(albumId) ?: throw CustomException(ErrorCode.ALBUM_NOT_FOUND)
        val user = userSecurity.user

        if (album.likes.none { it.user.id == user.id }) throw CustomException(ErrorCode.ALBUM_LIKE_NOT_FOUND)

        album.likes.removeIf { it.user.id == user.id }

        return albumRepository.save(album).toResponse()
    }

    private fun AlbumEntity.toResponse() = if (userSecurity.isAuthenticated) {
        AlbumResponse.of(this, userSecurity.user)
    } else {
        AlbumResponse.of(this)
    }
}
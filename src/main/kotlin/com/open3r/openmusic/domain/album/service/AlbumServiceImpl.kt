package com.open3r.openmusic.domain.album.service

import com.open3r.openmusic.domain.album.domain.entity.AlbumEntity
import com.open3r.openmusic.domain.album.domain.enums.AlbumScope
import com.open3r.openmusic.domain.album.dto.request.AlbumCreateRequest
import com.open3r.openmusic.domain.album.dto.request.AlbumUpdateRequest
import com.open3r.openmusic.domain.album.dto.response.AlbumResponse
import com.open3r.openmusic.domain.album.repository.AlbumRepository
import com.open3r.openmusic.domain.song.domain.entity.SongEntity
import com.open3r.openmusic.domain.song.repository.SongRepository
import com.open3r.openmusic.global.error.CustomException
import com.open3r.openmusic.global.error.ErrorCode
import com.open3r.openmusic.global.security.UserSecurity
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AlbumServiceImpl(
    private val albumRepository: AlbumRepository,
    private val songRepository: SongRepository,
    private val userSecurity: UserSecurity
) : AlbumService {
    @Transactional(readOnly = true)
    override fun getAlbums(): List<AlbumResponse> {
        return albumRepository.findAll().map { it.toResponse() }
    }

    @Transactional(readOnly = true)
    override fun getPublicAlbums(): List<AlbumResponse> {
        return albumRepository.findAllByScope(AlbumScope.PUBLIC).map { it.toResponse() }
    }

    @Transactional(readOnly = true)
    override fun getPrivateAlbums(): List<AlbumResponse> {
        val user = userSecurity.user
        val albums = albumRepository.findAllByScope(AlbumScope.PRIVATE)

        return albums.map { AlbumResponse.of(it, user) }
    }

    @Transactional(readOnly = true)
    override fun getMyAlbums(): List<AlbumResponse> {
        val user = userSecurity.user
        val albums = albumRepository.findAllByArtist(user)

        return albums.map { AlbumResponse.of(it, user) }
    }

    @Transactional(readOnly = true)
    override fun getAlbum(albumId: Long): AlbumResponse {
        val album = albumRepository.findByIdOrNull(albumId) ?: throw CustomException(ErrorCode.ALBUM_NOT_FOUND)

        return album.toResponse()
    }

    @Transactional(readOnly = true)
    override fun searchAlbum(query: String): List<AlbumResponse> {
        return albumRepository.findAllByTitleContainingIgnoreCase(query).map { it.toResponse() }
    }

    @Transactional
    override fun createAlbum(request: AlbumCreateRequest) {
        val user = userSecurity.user
        val album = albumRepository.save(
            AlbumEntity(
                title = request.title,
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
        }))

        albumRepository.save(album)
    }

    @Transactional
    override fun updateAlbum(albumId: Long, request: AlbumUpdateRequest) {
        val album = albumRepository.findByIdOrNull(albumId) ?: throw CustomException(ErrorCode.ALBUM_NOT_FOUND)
        val user = userSecurity.user

        if (album.artist.id != user.id) throw CustomException(ErrorCode.ALBUM_NOT_UPDATABLE)

        album.title = request.title ?: album.title
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
    override fun addAlbumLike(albumId: Long) {
        val album = albumRepository.findByIdOrNull(albumId) ?: throw CustomException(ErrorCode.ALBUM_NOT_FOUND)
        val user = userSecurity.user

        if (album.likes.any { it.id == user.id }) throw CustomException(ErrorCode.ALBUM_LIKE_ALREADY_EXISTS)

        album.likes.add(user)

        albumRepository.save(album)
    }

    @Transactional
    override fun removeAlbumLike(albumId: Long) {
        val album = albumRepository.findByIdOrNull(albumId) ?: throw CustomException(ErrorCode.ALBUM_NOT_FOUND)
        val user = userSecurity.user

        if (album.likes.none { it.id == user.id }) throw CustomException(ErrorCode.ALBUM_LIKE_NOT_FOUND)

        album.likes.removeIf { it.id == user.id }

        albumRepository.save(album)
    }

    private fun AlbumEntity.toResponse() = if (userSecurity.isAuthenticated) {
        AlbumResponse.of(this, userSecurity.user)
    } else {
        AlbumResponse.of(this)
    }

}
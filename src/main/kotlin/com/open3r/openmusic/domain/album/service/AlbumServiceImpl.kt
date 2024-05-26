package com.open3r.openmusic.domain.album.service

import com.open3r.openmusic.domain.album.dto.request.AlbumCreateRequest
import com.open3r.openmusic.domain.album.dto.request.AlbumUpdateRequest
import com.open3r.openmusic.domain.album.dto.response.AlbumResponse
import com.open3r.openmusic.domain.album.repository.AlbumRepository
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
        val albums = albumRepository.findAll()

        return albums.map { AlbumResponse.of(it) }
    }

    @Transactional(readOnly = true)
    override fun getAlbum(albumId: Long): AlbumResponse {
        val album = albumRepository.findByIdOrNull(albumId) ?: throw CustomException(ErrorCode.ALBUM_NOT_FOUND)

        return AlbumResponse.of(album)
    }

    @Transactional(readOnly = true)
    override fun searchAlbum(query: String): List<AlbumResponse> {
        val albums = albumRepository.findAllByTitleContainingIgnoreCase(query)

        return albums.map { AlbumResponse.of(it) }
    }

    @Transactional
    override fun createAlbum(request: AlbumCreateRequest) {
        val user = userSecurity.user
        val album = request.toEntity(user)

        albumRepository.save(album)
    }

    @Transactional
    override fun updateAlbum(albumId: Long, request: AlbumUpdateRequest) {
        val album = albumRepository.findByIdOrNull(albumId) ?: throw CustomException(ErrorCode.ALBUM_NOT_FOUND)
        val user = userSecurity.user

        if (album.artist.id != user.id) throw CustomException(ErrorCode.ALBUM_NOT_UPDATABLE)

        album.title = request.title ?: album.title
        album.coverUrl = request.coverUrl ?: album.coverUrl

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
    override fun createAlbumSong(albumId: Long, songId: Long) {
        val album = albumRepository.findByIdOrNull(albumId) ?: throw CustomException(ErrorCode.ALBUM_NOT_FOUND)
        val song = songRepository.findByIdOrNull(songId) ?: throw CustomException(ErrorCode.SONG_NOT_FOUND)
        val user = userSecurity.user

        if (album.artist.id != user.id) throw CustomException(ErrorCode.ALBUM_NOT_UPDATABLE)
        if (album.songs.any { it.id == song.id }) throw CustomException(ErrorCode.ALBUM_SONG_ALREADY_EXISTS)

        album.songs.add(song)

        albumRepository.save(album)
    }

    @Transactional
    override fun deleteAlbumSong(albumId: Long, songId: Long) {
        val album = albumRepository.findByIdOrNull(albumId) ?: throw CustomException(ErrorCode.ALBUM_NOT_FOUND)
        val song = songRepository.findByIdOrNull(songId) ?: throw CustomException(ErrorCode.SONG_NOT_FOUND)
        val user = userSecurity.user

        if (album.artist.id != user.id) throw CustomException(ErrorCode.ALBUM_NOT_UPDATABLE)
        if (album.songs.none { it.id == song.id }) throw CustomException(ErrorCode.ALBUM_SONG_NOT_FOUND)

        album.songs.removeIf { it.id == song.id }

        albumRepository.save(album)
    }

    @Transactional
    override fun createAlbumLike(albumId: Long) {
        val album = albumRepository.findByIdOrNull(albumId) ?: throw CustomException(ErrorCode.ALBUM_NOT_FOUND)
        val user = userSecurity.user

        if (album.likes.any { it.id == user.id }) throw CustomException(ErrorCode.ALBUM_LIKE_ALREADY_EXISTS)

        album.likes.add(user)

        albumRepository.save(album)
    }

    @Transactional
    override fun deleteAlbumLike(albumId: Long) {
        val album = albumRepository.findByIdOrNull(albumId) ?: throw CustomException(ErrorCode.ALBUM_NOT_FOUND)
        val user = userSecurity.user

        if (album.likes.none { it.id == user.id }) throw CustomException(ErrorCode.ALBUM_LIKE_NOT_FOUND)

        album.likes.removeIf { it.id == user.id }

        albumRepository.save(album)
    }
}
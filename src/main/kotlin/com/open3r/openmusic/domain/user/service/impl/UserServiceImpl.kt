package com.open3r.openmusic.domain.user.service.impl

import com.open3r.openmusic.domain.album.dto.response.AlbumResponse
import com.open3r.openmusic.domain.album.repository.AlbumRepository
import com.open3r.openmusic.domain.playlist.dto.response.PlaylistResponse
import com.open3r.openmusic.domain.playlist.repository.PlaylistRepository
import com.open3r.openmusic.domain.song.dto.response.SongResponse
import com.open3r.openmusic.domain.song.repository.SongQueryRepository
import com.open3r.openmusic.domain.song.repository.SongRepository
import com.open3r.openmusic.domain.user.domain.entity.UserNowPlayingEntity
import com.open3r.openmusic.domain.user.domain.entity.UserQueueEntity
import com.open3r.openmusic.domain.user.domain.entity.UserRecentEntity
import com.open3r.openmusic.domain.user.domain.enums.UserRole
import com.open3r.openmusic.domain.user.dto.request.UserAddGenreRequest
import com.open3r.openmusic.domain.user.dto.request.UserRemoveGenreRequest
import com.open3r.openmusic.domain.user.dto.request.UserSetNowPlayingRequest
import com.open3r.openmusic.domain.user.dto.request.UserUpdateRequest
import com.open3r.openmusic.domain.user.dto.response.UserNowPlayingResponse
import com.open3r.openmusic.domain.user.dto.response.UserResponse
import com.open3r.openmusic.domain.user.repository.UserQueryRepository
import com.open3r.openmusic.domain.user.repository.UserRepository
import com.open3r.openmusic.domain.user.service.UserService
import com.open3r.openmusic.global.error.CustomException
import com.open3r.openmusic.global.error.ErrorCode
import com.open3r.openmusic.global.security.UserSecurity
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val userSecurity: UserSecurity,
    private val passwordEncoder: PasswordEncoder,
    private val albumRepository: AlbumRepository,
    private val playlistRepository: PlaylistRepository,
    private val songRepository: SongRepository,
    private val songQueryRepository: SongQueryRepository,
    private val userQueryRepository: UserQueryRepository
) : UserService {

    @Transactional(readOnly = true)
    override fun getUsers(): List<UserResponse> {
        val users = userRepository.findAll()

        return users.map { UserResponse.of(it) }
    }

    @Transactional(readOnly = true)
    override fun getMe(): UserResponse {
        val user = userSecurity.user

        return UserResponse.of(user)
    }

    @Transactional
    override fun updateMe(request: UserUpdateRequest): UserResponse {
        var user = userSecurity.user

        if (!passwordEncoder.matches(request.currentPassword, user.password))
            throw CustomException(ErrorCode.USER_PASSWORD_NOT_MATCH)

        if (request.nickname != null) {
            user.nickname = request.nickname
        }

        if (request.avatarUrl != null) {
            user.avatarUrl = request.avatarUrl
        }

        if (request.password != null) {
            user.password = passwordEncoder.encode(request.password)
        }

        user = userRepository.save(user)

        return UserResponse.of(user)
    }

    @Transactional
    override fun addGenre(request: UserAddGenreRequest) {
        val genre = request.genre
        val user = userSecurity.user

        if (user.genres.contains(genre)) throw CustomException(ErrorCode.USER_GENRE_ALREADY_EXISTS)

        user.genres.add(genre)

        userRepository.save(user)
    }

    @Transactional
    override fun removeGenre(request: UserRemoveGenreRequest) {
        val genre = request.genre
        val user = userSecurity.user

        if (!user.genres.contains(genre)) throw CustomException(ErrorCode.USER_GENRE_NOT_FOUND)

        user.genres.remove(genre)

        userRepository.save(user)
    }

    @Transactional(readOnly = true)
    override fun getUser(userId: Long): UserResponse {
        val user = userRepository.findByIdOrNull(userId) ?: throw CustomException(ErrorCode.USER_NOT_FOUND)

        return UserResponse.of(user)
    }

    @Transactional
    override fun deleteUser(userId: Long) {
        val user = userRepository.findByIdOrNull(userId) ?: throw CustomException(ErrorCode.USER_NOT_FOUND)
        val current = userSecurity.user

        if (user.id != current.id && current.role != UserRole.ADMIN) throw CustomException(ErrorCode.USER_NOT_DELETABLE)

        userRepository.delete(user)
    }

    @Transactional(readOnly = true)
    override fun getMyAlbums(pageable: Pageable): Page<AlbumResponse> {
        val user = userSecurity.user
        val albums = albumRepository.findALlByArtist(user, pageable)

        return albums.map { AlbumResponse.of(it, user) }
    }

    @Transactional(readOnly = true)
    override fun getMySongs(pageable: Pageable): Page<SongResponse> {
        val user = userSecurity.user
        val songs = songQueryRepository.getMySongs(pageable)

        return songs.map { SongResponse.of(it, user) }
    }

    @Transactional(readOnly = true)
    override fun getMyPlaylists(pageable: Pageable): Page<PlaylistResponse> {
        val user = userSecurity.user
        val playlists = playlistRepository.findAllByArtist(user, pageable)

        return playlists.map { PlaylistResponse.of(it, user) }
    }

    @Transactional
    override fun getMyQueue(): List<SongResponse> {
        val user = userSecurity.user
        val songs = user.queue.map { it.song }

        return songs.map { SongResponse.of(it, user) }
    }

    @Transactional
    override fun addPlaylistSongsToQueue(playlistId: Long) {
        val user = userSecurity.user
        val playlist =
            playlistRepository.findByIdOrNull(playlistId) ?: throw CustomException(ErrorCode.PLAYLIST_NOT_FOUND)

        user.queue.addAll(playlist.songs.map { UserQueueEntity(song = it.song, user = user) })

        userRepository.save(user)
    }

    @Transactional
    override fun addAlbumSongsToQueue(albumId: Long) {
        val user = userSecurity.user
        val album = albumRepository.findByIdOrNull(albumId) ?: throw CustomException(ErrorCode.ALBUM_NOT_FOUND)

        user.queue.addAll(album.songs.map { UserQueueEntity(song = it.song, user = user) })

        userRepository.save(user)
    }

    @Transactional
    override fun addRankingSongsToQueue() {
        val user = userSecurity.user
        val songs = songQueryRepository.getRankingSongs(PageRequest.of(0, 100)).content

        user.queue.addAll(songs.map { UserQueueEntity(song = it, user = user) })

        userRepository.save(user)
    }

    @Transactional
    override fun addLatestSongsToQueue() {
        val user = userSecurity.user
        val songs = songQueryRepository.getLatestSongs(PageRequest.of(0, 100)).content

        user.queue.addAll(songs.map { UserQueueEntity(song = it, user = user) })

        userRepository.save(user)
    }

    @Transactional
    override fun addSongToQueue(songId: Long) {
        val user = userSecurity.user
        val song = songRepository.findByIdOrNull(songId) ?: throw CustomException(ErrorCode.SONG_NOT_FOUND)

        if (user.queue.any { it.song.id == song.id }) throw CustomException(ErrorCode.USER_QUEUE_ALREADY_EXISTS)

        user.queue.add(UserQueueEntity(song = song, user = user))

        userRepository.save(user)
    }

    @Transactional
    override fun removeSongFromQueue(songId: Long) {
        val user = userSecurity.user
        val song = songRepository.findByIdOrNull(songId) ?: throw CustomException(ErrorCode.SONG_NOT_FOUND)
        val queue = user.queue.find { it.song.id == song.id } ?: throw CustomException(ErrorCode.USER_QUEUE_NOT_FOUND)

        user.queue.remove(queue)

        userRepository.save(user)
    }

    @Transactional
    override fun clearQueue() {
        val user = userSecurity.user

        user.queue.clear()

        userRepository.save(user)
    }

    @Transactional
    override fun shuffleQueue(): List<SongResponse> {
        val user = userSecurity.user
        val songs = user.queue.shuffled().map { it.song }

        user.queue.clear()
        user.queue.addAll(songs.map { UserQueueEntity(song = it, user = user) })

        userRepository.save(user)

        return songs.map { SongResponse.of(it, user) }
    }

    @Transactional(readOnly = true)
    override fun getMyNowPlaying(): UserNowPlayingResponse {
        val user = userSecurity.user
        val nowPlaying = user.nowPlaying ?: throw CustomException(ErrorCode.SONG_NOT_PLAYING)

        return UserNowPlayingResponse(song = SongResponse.of(nowPlaying.song, user), progress = nowPlaying.progress)
    }

    @Transactional
    override fun setMyNowPlaying(request: UserSetNowPlayingRequest): SongResponse {
        val user = userSecurity.user
        val song = songRepository.findByIdOrNull(request.songId) ?: throw CustomException(ErrorCode.SONG_NOT_FOUND)

        if (user.nowPlaying == null) {
            user.nowPlaying = UserNowPlayingEntity(song = song, user = user, progress = request.progress)
        } else {
            user.nowPlaying!!.song = song
            user.nowPlaying!!.progress = request.progress
        }

        userRepository.save(user)

        return SongResponse.of(song, user)
    }

    @Transactional(readOnly = true)
    override fun getMyRecents(): List<SongResponse> {
        val user = userSecurity.user
        val songs = user.recents.map { it.song }.reversed()

        return songs.map { SongResponse.of(it, user) }
    }

    @Transactional
    override fun addSongToRecents(songId: Long) {
        val user = userSecurity.user
        val song = songRepository.findByIdOrNull(songId) ?: throw CustomException(ErrorCode.SONG_NOT_FOUND)

        if (user.recents.any { it.song.id == song.id }) {
            val recent = user.recents.find { it.song.id == song.id }!!
            user.recents.remove(recent)
        }

        if (user.recents.size >= 10) {
            user.recents.removeLast()
        }

        user.recents.addFirst(UserRecentEntity(song = song, user = user))

        userRepository.save(user)
    }

    @Transactional(readOnly = true)
    override fun getMyRecommendations(pageable: Pageable): Page<SongResponse> {
        val user = userSecurity.user
        val songs = songQueryRepository.getSongsByGenreIn(user.genres.toList(), pageable)

        return songs.map { SongResponse.of(it, user) }
    }

    @Transactional(readOnly = true)
    override fun getUserAlbums(userId: Long): List<AlbumResponse> {
        val user = userRepository.findByIdOrNull(userId) ?: throw CustomException(ErrorCode.USER_NOT_FOUND)
        val albums = albumRepository.findAllByArtist(user)

        return albums.map { AlbumResponse.of(it, user) }
    }

    @Transactional(readOnly = true)
    override fun getUserPlaylists(userId: Long): List<PlaylistResponse> {
        val user = userRepository.findByIdOrNull(userId) ?: throw CustomException(ErrorCode.USER_NOT_FOUND)
        val playlists = playlistRepository.findAllByArtist(user)

        return playlists.map { PlaylistResponse.of(it, user) }
    }

    @Transactional(readOnly = true)
    override fun getUserSongs(userId: Long): List<SongResponse> {
        val user = userRepository.findByIdOrNull(userId) ?: throw CustomException(ErrorCode.USER_NOT_FOUND)
        val songs = songQueryRepository.getSongsByArtist(user)

        return songs.map { SongResponse.of(it, user) }
    }

    @Transactional(readOnly = true)
    override fun searchUsers(query: String, pageable: Pageable): Page<UserResponse> {
        val users = userQueryRepository.searchUsers(query, pageable)

        return users.map { UserResponse.of(it) }
    }
}
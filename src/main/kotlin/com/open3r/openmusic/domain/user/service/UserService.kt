package com.open3r.openmusic.domain.user.service

import com.open3r.openmusic.domain.album.dto.response.AlbumResponse
import com.open3r.openmusic.domain.playlist.dto.response.PlaylistResponse
import com.open3r.openmusic.domain.song.dto.response.SongResponse
import com.open3r.openmusic.domain.user.dto.request.UserAddGenreRequest
import com.open3r.openmusic.domain.user.dto.request.UserRemoveGenreRequest
import com.open3r.openmusic.domain.user.dto.request.UserSetNowPlayingRequest
import com.open3r.openmusic.domain.user.dto.request.UserUpdateRequest
import com.open3r.openmusic.domain.user.dto.response.UserNowPlayingResponse
import com.open3r.openmusic.domain.user.dto.response.UserResponse
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface UserService {
    fun getUsers(): List<UserResponse>

    fun getMe(): UserResponse
    fun updateMe(request: UserUpdateRequest): UserResponse

    fun addGenre(request: UserAddGenreRequest)
    fun removeGenre(request: UserRemoveGenreRequest)

    fun getUser(userId: Long): UserResponse
    fun deleteUser(userId: Long)

    fun getMyNowPlaying(): UserNowPlayingResponse
    fun setMyNowPlaying(request: UserSetNowPlayingRequest): SongResponse

    fun getMyAlbums(pageable: Pageable): Page<AlbumResponse>
    fun getMySongs(pageable: Pageable): Page<SongResponse>
    fun getMyPlaylists(pageable: Pageable): Page<PlaylistResponse>
    fun getMyRecommendations(pageable: Pageable): Page<SongResponse>

    fun getMyQueue(): List<SongResponse>
    fun addPlaylistSongsToQueue(playlistId: Long)
    fun addAlbumSongsToQueue(albumId: Long)
    fun addRankingSongsToQueue()
    fun addLatestSongsToQueue()
    fun addSongToQueue(songId: Long)
    fun removeSongFromQueue(songId: Long)
    fun clearQueue()
    fun shuffleQueue(): List<SongResponse>

    fun getMyRecents(): List<SongResponse>
    fun addSongToRecents(songId: Long)

    fun getUserAlbums(userId: Long): List<AlbumResponse>
    fun getUserSongs(userId: Long): List<SongResponse>
    fun getUserPlaylists(userId: Long): List<PlaylistResponse>
}
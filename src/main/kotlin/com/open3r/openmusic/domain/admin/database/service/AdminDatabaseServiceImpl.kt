package com.open3r.openmusic.domain.admin.database.service

import com.open3r.openmusic.domain.album.repository.AlbumRepository
import com.open3r.openmusic.domain.playlist.repository.PlaylistRepository
import com.open3r.openmusic.domain.song.repository.SongRepository
import com.open3r.openmusic.domain.user.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class AdminDatabaseServiceImpl(
    private val albumRepository: AlbumRepository,
    private val playlistRepository: PlaylistRepository,
    private val songRepository: SongRepository,
    private val userRepository: UserRepository
) : AdminDatabaseService {
    override fun clearDatabase() {
        albumRepository.deleteAll()
        playlistRepository.deleteAll()
        songRepository.deleteAll()
        userRepository.deleteAll()
    }
}
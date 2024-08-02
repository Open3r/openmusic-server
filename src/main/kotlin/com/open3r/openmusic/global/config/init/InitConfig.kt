package com.open3r.openmusic.global.config.init

import com.open3r.openmusic.domain.album.repository.AlbumQueryRepository
import com.open3r.openmusic.domain.song.repository.SongQueryRepository
import jakarta.annotation.PostConstruct
import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.PageRequest

@Configuration
class InitConfig(
    private val albumQueryRepository: AlbumQueryRepository,
    private val playlistQueryRepository: AlbumQueryRepository,
    private val songQueryRepository: SongQueryRepository,
) {
    @PostConstruct
    fun init() {
        albumQueryRepository.getAlbums(PageRequest.of(0, 10))
        playlistQueryRepository.getAlbums(PageRequest.of(0, 10))
        songQueryRepository.getSongs(PageRequest.of(0, 10))
    }
}
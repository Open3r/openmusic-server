package com.open3r.openmusic.domain.playlist.repository

import com.open3r.openmusic.domain.playlist.domain.Playlist
import org.springframework.data.jpa.repository.JpaRepository

interface PlaylistRepository: JpaRepository<Playlist, Long> {
}
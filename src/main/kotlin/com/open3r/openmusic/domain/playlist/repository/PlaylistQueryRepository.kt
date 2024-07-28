package com.open3r.openmusic.domain.playlist.repository

import com.open3r.openmusic.domain.playlist.domain.entity.PlaylistEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface PlaylistQueryRepository {
    fun getPlaylists(pageable: Pageable): Page<PlaylistEntity>
}
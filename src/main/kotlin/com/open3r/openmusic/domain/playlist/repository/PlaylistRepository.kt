package com.open3r.openmusic.domain.playlist.repository

import com.open3r.openmusic.domain.playlist.domain.entity.PlaylistEntity
import com.open3r.openmusic.domain.user.domain.entity.UserEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface PlaylistRepository : JpaRepository<PlaylistEntity, Long> {
    fun findAllByArtist(artist: UserEntity): List<PlaylistEntity>
    fun findAllByArtist(artist: UserEntity, pageable: Pageable): Page<PlaylistEntity>
    fun findAllByTitleContainingIgnoreCase(title: String): List<PlaylistEntity>
}
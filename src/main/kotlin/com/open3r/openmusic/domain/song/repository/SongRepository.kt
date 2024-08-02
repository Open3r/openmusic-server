package com.open3r.openmusic.domain.song.repository

import com.open3r.openmusic.domain.song.domain.entity.SongEntity
import com.open3r.openmusic.domain.user.domain.entity.UserEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface SongRepository : JpaRepository<SongEntity, Long> {
    fun findAllByArtist(artist: UserEntity, pageable: Pageable): Page<SongEntity>
}
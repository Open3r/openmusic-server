package com.open3r.openmusic.domain.album.repository

import com.open3r.openmusic.domain.album.domain.entity.AlbumEntity
import com.open3r.openmusic.domain.user.domain.entity.UserEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface AlbumRepository : JpaRepository<AlbumEntity, Long> {
    fun findAllByArtist(artist: UserEntity): List<AlbumEntity>
    fun findALlByArtist(artist: UserEntity, pageable: Pageable): Page<AlbumEntity>
}
package com.open3r.openmusic.domain.album.repository

import com.open3r.openmusic.domain.album.domain.entity.AlbumEntity
import com.open3r.openmusic.domain.album.domain.enums.AlbumScope
import com.open3r.openmusic.domain.user.domain.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository

interface AlbumRepository : JpaRepository<AlbumEntity, Long> {
    fun findAllByScope(scope: AlbumScope): List<AlbumEntity>
    fun findAllByArtist(artist: UserEntity): List<AlbumEntity>
    fun findAllByTitleContainingIgnoreCase(title: String): List<AlbumEntity>
}
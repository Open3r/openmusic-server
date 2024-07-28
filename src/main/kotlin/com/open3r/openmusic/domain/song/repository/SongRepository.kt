package com.open3r.openmusic.domain.song.repository

import com.open3r.openmusic.domain.album.domain.enums.AlbumScope
import com.open3r.openmusic.domain.song.domain.entity.SongEntity
import com.open3r.openmusic.domain.song.domain.enums.SongGenre
import com.open3r.openmusic.domain.user.domain.entity.UserEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface SongRepository : JpaRepository<SongEntity, Long> {
    fun findAllByArtist(artist: UserEntity): List<SongEntity>
    fun findAllByArtist(artist: UserEntity, pageable: Pageable): Page<SongEntity>
    fun findAllByGenre(genre: SongGenre): List<SongEntity>
    fun findAllByScopeOrderByLikesDesc(scope: AlbumScope): List<SongEntity>
    fun findAllByTitleContainingIgnoreCase(title: String): List<SongEntity>
}
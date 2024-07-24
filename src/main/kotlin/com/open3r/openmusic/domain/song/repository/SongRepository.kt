package com.open3r.openmusic.domain.song.repository

import com.open3r.openmusic.domain.album.domain.enums.AlbumScope
import com.open3r.openmusic.domain.song.domain.entity.SongEntity
import com.open3r.openmusic.domain.user.domain.UserEntity
import org.springframework.data.jpa.repository.JpaRepository

interface SongRepository : JpaRepository<SongEntity, Long>, SongQueryRepository {
    fun findAllByArtist(artist: UserEntity): List<SongEntity>
    fun findAllByScopeOrderByLikesDesc(scope: AlbumScope): List<SongEntity>
    fun findAllByTitleContainingIgnoreCase(title: String): List<SongEntity>
    fun findAllByScope(scope: AlbumScope): List<SongEntity>
}
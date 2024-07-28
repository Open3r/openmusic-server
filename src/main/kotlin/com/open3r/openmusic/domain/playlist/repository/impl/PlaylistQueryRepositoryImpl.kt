package com.open3r.openmusic.domain.playlist.repository.impl

import com.open3r.openmusic.domain.playlist.domain.entity.PlaylistEntity
import com.open3r.openmusic.domain.playlist.domain.entity.QPlaylistEntity.playlistEntity
import com.open3r.openmusic.domain.playlist.domain.enums.PlaylistScope
import com.open3r.openmusic.domain.playlist.repository.PlaylistQueryRepository
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

@Repository
class PlaylistQueryRepositoryImpl(
    private val jpaQueryFactory: JPAQueryFactory
) : PlaylistQueryRepository {
    override fun getPlaylists(pageable: Pageable): Page<PlaylistEntity> {
        val playlists = jpaQueryFactory.selectFrom(playlistEntity)
            .where(playlistEntity.scope.eq(PlaylistScope.PUBLIC))
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .orderBy(playlistEntity.createdAt.desc())
            .fetch()

        return PageImpl(playlists, pageable, playlists.size.toLong())
    }
}
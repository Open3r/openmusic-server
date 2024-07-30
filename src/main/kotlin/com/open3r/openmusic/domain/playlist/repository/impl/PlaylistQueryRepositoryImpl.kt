package com.open3r.openmusic.domain.playlist.repository.impl

import com.open3r.openmusic.domain.playlist.domain.entity.PlaylistEntity
import com.open3r.openmusic.domain.playlist.domain.entity.QPlaylistEntity.playlistEntity
import com.open3r.openmusic.domain.playlist.domain.enums.PlaylistScope
import com.open3r.openmusic.domain.playlist.repository.PlaylistQueryRepository
import com.open3r.openmusic.global.util.paginate
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.*
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
class PlaylistQueryRepositoryImpl(
    private val jpaQueryFactory: JPAQueryFactory
) : PlaylistQueryRepository {
    @Transactional
    override fun getPlaylists(pageable: Pageable): Page<PlaylistEntity> {
        val count = jpaQueryFactory.select(playlistEntity.count())
            .from(playlistEntity)
            .where(playlistEntity.scope.eq(PlaylistScope.PUBLIC))
            .fetchOne() ?: 0

        val playlists = jpaQueryFactory.selectFrom(playlistEntity)
            .where(playlistEntity.scope.eq(PlaylistScope.PUBLIC))
            .orderBy(playlistEntity.createdAt.desc())
            .paginate(pageable)
            .fetch()

        return PageImpl(playlists, pageable, count)
    }

    @Transactional
    override fun searchPlaylists(query: String, pageable: Pageable): Slice<PlaylistEntity> {
        val playlists = jpaQueryFactory.selectFrom(playlistEntity)
            .where(
                playlistEntity.scope.eq(PlaylistScope.PUBLIC),
                playlistEntity.title.containsIgnoreCase(query)
                    .or(playlistEntity.artist.nickname.containsIgnoreCase(query))
            )
            .orderBy(playlistEntity.createdAt.desc())
            .paginate(pageable)
            .fetch()

        return SliceImpl(playlists, pageable, true)
    }
}
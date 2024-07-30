package com.open3r.openmusic.domain.playlist.repository.impl

import com.open3r.openmusic.domain.playlist.domain.entity.PlaylistEntity
import com.open3r.openmusic.domain.playlist.domain.entity.QPlaylistEntity.playlistEntity
import com.open3r.openmusic.domain.playlist.domain.enums.PlaylistScope
import com.open3r.openmusic.domain.playlist.repository.PlaylistQueryRepository
import com.open3r.openmusic.global.util.paginate
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.*
import org.springframework.stereotype.Repository

@Repository
class PlaylistQueryRepositoryImpl(
    private val jpaQueryFactory: JPAQueryFactory
) : PlaylistQueryRepository {
    override fun getPlaylists(pageable: Pageable): Page<PlaylistEntity> {
        val playlists = jpaQueryFactory.selectFrom(playlistEntity)
            .where(playlistEntity.scope.eq(PlaylistScope.PUBLIC))
            .paginate(pageable)
            .orderBy(playlistEntity.createdAt.desc())
            .fetch()

        return PageImpl(playlists, pageable, playlists.size.toLong())
    }

    override fun searchPlaylists(query: String, pageable: Pageable): Slice<PlaylistEntity> {
        val playlists = jpaQueryFactory.selectFrom(playlistEntity)
            .where(
                playlistEntity.scope.eq(PlaylistScope.PUBLIC),
                playlistEntity.title.containsIgnoreCase(query)
                    .or(playlistEntity.artist.nickname.containsIgnoreCase(query))
            )
            .paginate(pageable)
            .orderBy(playlistEntity.createdAt.desc())
            .fetch()

        return SliceImpl(playlists, pageable, true)
    }
}
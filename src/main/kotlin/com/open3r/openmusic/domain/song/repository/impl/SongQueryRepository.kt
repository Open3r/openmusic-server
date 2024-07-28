package com.open3r.openmusic.domain.song.repository.impl

import com.open3r.openmusic.domain.album.domain.enums.AlbumScope
import com.open3r.openmusic.domain.song.domain.entity.QSongEntity.songEntity
import com.open3r.openmusic.domain.song.domain.entity.SongEntity
import com.open3r.openmusic.domain.song.repository.SongQueryRepository
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

@Repository
class SongQueryRepository(
    private val jpaQueryFactory: JPAQueryFactory
) : SongQueryRepository {
    override fun getSongs(pageable: Pageable): Page<SongEntity> {
        val songs = jpaQueryFactory.selectFrom(songEntity)
            .where(songEntity.scope.eq(AlbumScope.PUBLIC))
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .orderBy(songEntity.createdAt.desc())
            .fetch()

        return PageImpl(songs, pageable, songs.size.toLong())
    }
}
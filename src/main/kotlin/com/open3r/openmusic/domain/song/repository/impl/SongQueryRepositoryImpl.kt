package com.open3r.openmusic.domain.song.repository.impl

import com.open3r.openmusic.domain.album.domain.enums.AlbumScope
import com.open3r.openmusic.domain.song.domain.entity.QSongEntity.songEntity
import com.open3r.openmusic.domain.song.domain.entity.SongEntity
import com.open3r.openmusic.domain.song.domain.enums.SongGenre
import com.open3r.openmusic.domain.song.repository.SongQueryRepository
import com.open3r.openmusic.global.util.orderBy
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.*
import org.springframework.stereotype.Repository

@Repository
class SongQueryRepositoryImpl(
    private val jpaQueryFactory: JPAQueryFactory
) : SongQueryRepository {
    override fun getSongs(pageable: Pageable): Page<SongEntity> {
        val songs = jpaQueryFactory.selectFrom(songEntity)
            .where(songEntity.scope.eq(AlbumScope.PUBLIC))
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .orderBy(pageable.sort)
            .fetch()

        return PageImpl(songs, pageable, songs.size.toLong())
    }

    override fun getRankingSongs(pageable: Pageable): Slice<SongEntity> {
        val songs = jpaQueryFactory.selectFrom(songEntity)
            .where(songEntity.scope.eq(AlbumScope.PUBLIC))
            .offset(pageable.offset)
            .limit(if (pageable.pageSize > 100) 100 else pageable.pageSize.toLong())
            .orderBy(songEntity.likes.size().desc())
            .fetch()

        return SliceImpl(songs, pageable, true)
    }

    override fun getGenreSongs(genre: SongGenre, pageable: Pageable): Page<SongEntity> {
        val songs = jpaQueryFactory.selectFrom(songEntity)
            .where(songEntity.scope.eq(AlbumScope.PUBLIC), songEntity.genre.eq(genre))
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .orderBy(pageable.sort)
            .fetch()

        return PageImpl(songs, pageable, songs.size.toLong())
    }
}


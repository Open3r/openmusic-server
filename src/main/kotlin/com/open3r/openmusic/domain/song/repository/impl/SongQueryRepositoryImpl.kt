package com.open3r.openmusic.domain.song.repository.impl

import com.open3r.openmusic.domain.album.domain.enums.AlbumScope
import com.open3r.openmusic.domain.song.domain.entity.QSongEntity.songEntity
import com.open3r.openmusic.domain.song.domain.entity.SongEntity
import com.open3r.openmusic.domain.song.domain.enums.SongGenre
import com.open3r.openmusic.domain.song.domain.enums.SongStatus
import com.open3r.openmusic.domain.song.repository.SongQueryRepository
import com.open3r.openmusic.domain.user.domain.entity.UserEntity
import com.open3r.openmusic.global.security.UserSecurity
import com.open3r.openmusic.global.util.orderBy
import com.open3r.openmusic.global.util.paginate
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.*
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
class SongQueryRepositoryImpl(
    private val userSecurity: UserSecurity,
    private val jpaQueryFactory: JPAQueryFactory
) : SongQueryRepository {
    @Transactional
    override fun getSongs(pageable: Pageable): Page<SongEntity> {
        val songs = jpaQueryFactory.selectFrom(songEntity)
            .where(songEntity.scope.eq(AlbumScope.PUBLIC), songEntity.status.eq(SongStatus.APPROVED))
            .paginate(pageable)
            .orderBy(pageable.sort)
            .fetch()

        val count = jpaQueryFactory.select(songEntity.count())
            .from(songEntity)
            .where(songEntity.scope.eq(AlbumScope.PUBLIC), songEntity.status.eq(SongStatus.APPROVED))
            .fetchOne() ?: 0

        return PageImpl(songs, pageable, count)
    }

    @Transactional
    override fun getMySongs(pageable: Pageable): Page<SongEntity> {
        val user = userSecurity.user

        val songs = jpaQueryFactory.selectFrom(songEntity)
            .where(songEntity.status.eq(SongStatus.APPROVED), songEntity.artist.id.eq(user.id))
            .paginate(pageable)
            .orderBy(pageable.sort)
            .fetch()

        val count = jpaQueryFactory.select(songEntity.count())
            .from(songEntity)
            .where(songEntity.status.eq(SongStatus.APPROVED), songEntity.artist.id.eq(user.id))
            .fetchOne() ?: 0

        return PageImpl(songs, pageable, count)
    }

    @Transactional
    override fun getSongsByArtist(artist: UserEntity): List<SongEntity> {
        return jpaQueryFactory.selectFrom(songEntity)
            .where(songEntity.status.eq(SongStatus.APPROVED), songEntity.artist.id.eq(artist.id))
            .fetch()
    }

    @Transactional
    override fun getRankingSongs(pageable: Pageable): Slice<SongEntity> {
        val songs = jpaQueryFactory.selectFrom(songEntity)
            .where(songEntity.scope.eq(AlbumScope.PUBLIC), songEntity.status.eq(SongStatus.APPROVED))
            .offset(pageable.offset)
            .limit(if (pageable.pageSize > 100) 100 else pageable.pageSize.toLong())
            .orderBy(songEntity.likes.size().desc())
            .fetch()

        return SliceImpl(songs, pageable, true)
    }

    @Transactional
    override fun getGenreSongs(genre: SongGenre, pageable: Pageable): Page<SongEntity> {
        val songs = jpaQueryFactory.selectFrom(songEntity)
            .where(songEntity.scope.eq(AlbumScope.PUBLIC), songEntity.genre.eq(genre), songEntity.status.eq(SongStatus.APPROVED))
            .paginate(pageable)
            .orderBy(pageable.sort)
            .fetch()

        val count = jpaQueryFactory.select(songEntity.count())
            .from(songEntity)
            .where(songEntity.scope.eq(AlbumScope.PUBLIC), songEntity.genre.eq(genre), songEntity.status.eq(SongStatus.APPROVED))
            .fetchOne() ?: 0

        return PageImpl(songs, pageable, count)
    }

    @Transactional
    override fun getLatestSongs(pageable: Pageable): Slice<SongEntity> {
        val songs = jpaQueryFactory.selectFrom(songEntity)
            .where(songEntity.scope.eq(AlbumScope.PUBLIC), songEntity.status.eq(SongStatus.APPROVED))
            .paginate(pageable)
            .orderBy(songEntity.createdAt.desc())
            .fetch()

        return SliceImpl(songs, pageable, true)
    }

    @Transactional
    override fun getSongsByGenreIn(genres: List<SongGenre>, pageable: Pageable): Page<SongEntity> {
        val count = jpaQueryFactory.select(songEntity.count())
            .from(songEntity)
            .where(songEntity.scope.eq(AlbumScope.PUBLIC), songEntity.genre.`in`(genres), songEntity.status.eq(SongStatus.APPROVED))
            .fetchOne() ?: 0

        val songs = jpaQueryFactory.selectFrom(songEntity)
            .where(songEntity.scope.eq(AlbumScope.PUBLIC), songEntity.genre.`in`(genres), songEntity.status.eq(SongStatus.APPROVED))
            .paginate(pageable)
            .fetch()
            .shuffled()

        return PageImpl(songs, pageable, count)
    }

    @Transactional
    override fun searchSongs(query: String, pageable: Pageable): Slice<SongEntity> {
        val songs = jpaQueryFactory.selectFrom(songEntity)
            .where(
                songEntity.scope.eq(AlbumScope.PUBLIC),
                songEntity.status.eq(SongStatus.APPROVED),
                songEntity.title.containsIgnoreCase(query).or(songEntity.artist.nickname.containsIgnoreCase(query)),
            )
            .orderBy(pageable.sort)
            .paginate(pageable)
            .fetch()

        return SliceImpl(songs, pageable, true)
    }
}


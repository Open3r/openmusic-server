package com.open3r.openmusic.domain.album.repository.impl

import com.open3r.openmusic.domain.album.domain.entity.AlbumEntity
import com.open3r.openmusic.domain.album.domain.entity.QAlbumEntity.albumEntity
import com.open3r.openmusic.domain.album.domain.enums.AlbumScope
import com.open3r.openmusic.domain.album.repository.AlbumQueryRepository
import com.open3r.openmusic.global.util.paginate
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
class AlbumQueryRepositoryImpl(
    private val jpaQueryFactory: JPAQueryFactory
) : AlbumQueryRepository {
    @Transactional
    override fun searchAlbums(query: String, pageable: Pageable): Page<AlbumEntity> {
        val count = jpaQueryFactory.select(albumEntity.count())
            .from(albumEntity)
            .where(
                albumEntity.scope.eq(AlbumScope.PUBLIC),
                albumEntity.title.containsIgnoreCase(query).or(albumEntity.artist.nickname.containsIgnoreCase(query))
            )
            .fetchOne() ?: 0

        val albums = jpaQueryFactory.selectFrom(albumEntity)
            .where(
                albumEntity.scope.eq(AlbumScope.PUBLIC),
                albumEntity.title.containsIgnoreCase(query).or(albumEntity.artist.nickname.containsIgnoreCase(query))
            )
            .orderBy(albumEntity.createdAt.desc())
            .paginate(pageable)
            .fetch()

        return PageImpl(albums, pageable, count)
    }

    @Transactional
    override fun getAlbums(pageable: Pageable): Page<AlbumEntity> {
        val count = jpaQueryFactory.select(albumEntity.count())
            .from(albumEntity)
            .where(albumEntity.scope.eq(AlbumScope.PUBLIC))
            .fetchOne() ?: 0

        val albums = jpaQueryFactory.selectFrom(albumEntity)
            .where(albumEntity.scope.eq(AlbumScope.PUBLIC))
            .orderBy(albumEntity.createdAt.desc())
            .paginate(pageable)
            .fetch()

        return PageImpl(albums, pageable, count)
    }
}
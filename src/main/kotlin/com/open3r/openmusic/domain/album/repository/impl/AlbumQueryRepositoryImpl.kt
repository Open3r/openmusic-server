package com.open3r.openmusic.domain.album.repository.impl

import com.open3r.openmusic.domain.album.domain.entity.AlbumEntity
import com.open3r.openmusic.domain.album.domain.entity.QAlbumEntity.albumEntity
import com.open3r.openmusic.domain.album.domain.enums.AlbumScope
import com.open3r.openmusic.domain.album.repository.AlbumQueryRepository
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

@Repository
class AlbumQueryRepositoryImpl(
    private val jpaQueryFactory: JPAQueryFactory
) : AlbumQueryRepository {
    override fun getAlbums(pageable: Pageable): Page<AlbumEntity> {
        val albums = jpaQueryFactory.selectFrom(albumEntity)
            .where(albumEntity.scope.eq(AlbumScope.PUBLIC))
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .orderBy(albumEntity.createdAt.desc())
            .fetch()

        return PageImpl(albums, pageable, albums.size.toLong())
    }
}
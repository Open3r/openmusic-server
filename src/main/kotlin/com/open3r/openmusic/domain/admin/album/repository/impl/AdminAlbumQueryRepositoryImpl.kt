package com.open3r.openmusic.domain.admin.album.repository.impl

import com.open3r.openmusic.domain.admin.album.repository.AdminAlbumQueryRepository
import com.open3r.openmusic.domain.album.domain.entity.AlbumEntity
import com.open3r.openmusic.domain.album.domain.entity.QAlbumEntity.albumEntity
import com.open3r.openmusic.domain.album.domain.enums.AlbumStatus
import com.open3r.openmusic.global.util.paginate
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

@Repository
class AdminAlbumQueryRepositoryImpl(
    private val jpaQueryFactory: JPAQueryFactory
) : AdminAlbumQueryRepository {
    override fun getPendingAlbums(pageable: Pageable): Page<AlbumEntity> {
        val count = jpaQueryFactory.select(albumEntity.count())
            .from(albumEntity)
            .where(albumEntity.status.eq(AlbumStatus.PENDING))
            .fetchOne() ?: 0

        val albums = jpaQueryFactory.selectFrom(albumEntity)
            .where(albumEntity.status.eq(AlbumStatus.PENDING))
            .paginate(pageable)
            .orderBy(albumEntity.createdAt.desc())
            .fetch()

        return PageImpl(albums, pageable, count)
    }

    override fun getApprovedAlbums(pageable: Pageable): Page<AlbumEntity> {
        val count = jpaQueryFactory.select(albumEntity.count())
            .from(albumEntity)
            .where(albumEntity.status.eq(AlbumStatus.APPROVED))
            .fetchOne() ?: 0

        val albums = jpaQueryFactory.selectFrom(albumEntity)
            .where(albumEntity.status.eq(AlbumStatus.APPROVED))
            .paginate(pageable)
            .orderBy(albumEntity.createdAt.desc())
            .fetch()

        return PageImpl(albums, pageable, count)
    }

    override fun getRejectedAlbums(pageable: Pageable): Page<AlbumEntity> {
        val count = jpaQueryFactory.select(albumEntity.count())
            .from(albumEntity)
            .where(albumEntity.status.eq(AlbumStatus.REJECTED))
            .fetchOne() ?: 0

        val albums = jpaQueryFactory.selectFrom(albumEntity)
            .where(albumEntity.status.eq(AlbumStatus.REJECTED))
            .paginate(pageable)
            .orderBy(albumEntity.createdAt.desc())
            .fetch()

        return PageImpl(albums, pageable, count)
    }

    override fun getDeletedAlbums(pageable: Pageable): Page<AlbumEntity> {
        val count = jpaQueryFactory.select(albumEntity.count())
            .from(albumEntity)
            .where(albumEntity.status.eq(AlbumStatus.DELETED))
            .fetchOne() ?: 0

        val albums = jpaQueryFactory.selectFrom(albumEntity)
            .where(albumEntity.status.eq(AlbumStatus.DELETED))
            .paginate(pageable)
            .orderBy(albumEntity.createdAt.desc())
            .fetch()

        return PageImpl(albums, pageable, count)
    }
}
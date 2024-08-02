package com.open3r.openmusic.domain.admin.song.repository.impl

import com.open3r.openmusic.domain.admin.song.repository.AdminSongQueryRepository
import com.open3r.openmusic.domain.song.domain.entity.QSongEntity.songEntity
import com.open3r.openmusic.domain.song.domain.entity.SongEntity
import com.open3r.openmusic.domain.song.domain.enums.SongStatus
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
class AdminSongQueryRepositoryImpl(
    private val jpaQueryFactory: JPAQueryFactory
): AdminSongQueryRepository {
    @Transactional
    override fun getPendingSongs(): List<SongEntity> {
        return jpaQueryFactory.selectFrom(songEntity)
            .where(songEntity.status.eq(SongStatus.PENDING))
            .fetch()
    }

    @Transactional
    override fun getApprovedSongs(): List<SongEntity> {
        return jpaQueryFactory.selectFrom(songEntity)
            .where(songEntity.status.eq(SongStatus.APPROVED))
            .fetch()
    }

    @Transactional
    override fun getRejectedSongs(): List<SongEntity> {
        return jpaQueryFactory.selectFrom(songEntity)
            .where(songEntity.status.eq(SongStatus.REJECTED))
            .fetch()
    }

    @Transactional
    override fun getDeletedSongs(): List<SongEntity> {
        return jpaQueryFactory.selectFrom(songEntity)
            .where(songEntity.status.eq(SongStatus.DELETED))
            .fetch()
    }
}
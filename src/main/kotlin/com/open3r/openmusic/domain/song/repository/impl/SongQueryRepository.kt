package com.open3r.openmusic.domain.song.repository.impl

import com.open3r.openmusic.domain.song.repository.SongQueryRepository
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
class SongQueryRepository(
    private val jpaQueryFactory: JPAQueryFactory
) : SongQueryRepository
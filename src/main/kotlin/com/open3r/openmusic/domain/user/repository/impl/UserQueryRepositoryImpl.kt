package com.open3r.openmusic.domain.user.repository.impl

import com.open3r.openmusic.domain.user.domain.entity.QUserEntity.userEntity
import com.open3r.openmusic.domain.user.domain.entity.UserEntity
import com.open3r.openmusic.domain.user.domain.enums.UserStatus
import com.open3r.openmusic.domain.user.repository.UserQueryRepository
import com.open3r.openmusic.global.util.paginate
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
class UserQueryRepositoryImpl(
    private val jpaQueryFactory: JPAQueryFactory
) : UserQueryRepository {
    @Transactional
    override fun getUsers(): List<UserEntity> {
        return jpaQueryFactory.selectFrom(userEntity)
            .where(userEntity.status.eq(UserStatus.ACTIVE))
            .fetch()
    }

    @Transactional
    override fun getDeletedUsers(): List<UserEntity> {
        return jpaQueryFactory.selectFrom(userEntity)
            .where(userEntity.status.eq(UserStatus.DELETED))
            .fetch()
    }

    @Transactional
    override fun searchUsers(query: String, pageable: Pageable): Page<UserEntity> {
        val count = jpaQueryFactory.select(userEntity.count())
            .from(userEntity)
            .where(userEntity.status.eq(UserStatus.ACTIVE), userEntity.nickname.containsIgnoreCase(query))
            .fetchFirst() ?: 0

        val users = jpaQueryFactory.selectFrom(userEntity)
            .where(userEntity.status.eq(UserStatus.ACTIVE), userEntity.nickname.containsIgnoreCase(query))
            .paginate(pageable)
            .orderBy(userEntity.createdAt.desc())
            .fetch()

        return PageImpl(users, pageable, count)
    }
}
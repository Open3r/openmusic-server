package com.open3r.openmusic.domain.user.repository

import com.open3r.openmusic.domain.user.domain.entity.UserEntity
import com.open3r.openmusic.domain.user.domain.enums.UserProvider
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<UserEntity, Long> {
    fun findByEmail(email: String): UserEntity?
    fun findByEmailAndProvider(email: String, provider: UserProvider): UserEntity?
    fun existsByEmail(email: String): Boolean
}
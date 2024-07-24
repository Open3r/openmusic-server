package com.open3r.openmusic.domain.user.repository

import com.open3r.openmusic.domain.user.domain.UserEntity
import com.open3r.openmusic.domain.user.domain.UserProvider
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<UserEntity, Long> {
    fun findByEmail(email: String): UserEntity?
    fun findByEmailAndProvider(email: String, provider: UserProvider): UserEntity?
    fun findByEmailContainingIgnoreCase(email: String): List<UserEntity>
    fun existsByEmail(email: String): Boolean
}
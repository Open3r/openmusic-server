package com.open3r.openmusic.domain.user.repository

import com.open3r.openmusic.domain.user.domain.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {
    fun existsByUsername(username: String): Boolean

    fun findByUsername(username: String): User?
}
package com.open3r.openmusic.domain.user.repository

import com.open3r.openmusic.domain.user.domain.User
import com.open3r.openmusic.domain.user.domain.UserProvider
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {
    fun findByEmail(email: String): User?
    fun findByEmailAndProvider(email: String, provider: UserProvider): User?
    fun findByEmailContainingIgnoreCase(email: String): List<User>
    fun existsByEmail(email: String): Boolean
}
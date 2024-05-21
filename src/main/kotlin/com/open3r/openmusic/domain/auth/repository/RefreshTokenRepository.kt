package com.open3r.openmusic.domain.auth.repository

interface RefreshTokenRepository {
    fun save(userId: Long, refreshToken: String)
    fun findByUserId(userId: Long): String?
    fun deleteByUserId(userId: Long)
    fun existsByUserId(userId: Long): Boolean
}
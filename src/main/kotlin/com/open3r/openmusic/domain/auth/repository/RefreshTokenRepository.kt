package com.open3r.openmusic.domain.auth.repository

import java.util.concurrent.TimeUnit

interface RefreshTokenRepository {
    fun save(username: String, refreshToken: String)
    fun save(username: String, refreshToken: String, timeout: Long, unit: TimeUnit)
    fun findByUsername(username: String): String?
    fun deleteByUsername(username: String)
    fun existsByUsername(username: String): Boolean
}
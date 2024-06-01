package com.open3r.openmusic.domain.auth.repository

interface EmailTokenRepository {
    fun save(email: String, token: String)
    fun findByEmail(email: String): String?
    fun deleteByEmail(email: String)
    fun existsByEmail(email: String): Boolean
}
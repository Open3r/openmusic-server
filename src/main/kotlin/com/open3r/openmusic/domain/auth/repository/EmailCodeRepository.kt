package com.open3r.openmusic.domain.auth.repository

interface EmailCodeRepository {
    fun save(email: String, code: String)
    fun findByEmail(email: String): String?
    fun deleteByEmail(email: String)
    fun existsByEmail(email: String): Boolean
}
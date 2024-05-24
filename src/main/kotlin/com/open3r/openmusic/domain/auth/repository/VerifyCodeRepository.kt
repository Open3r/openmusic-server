package com.open3r.openmusic.domain.auth.repository

interface VerifyCodeRepository {
    fun save(email: String, number: String)
    fun findByEmail(email: String): String?
    fun deleteByEmail(email: String)
    fun existsByEmail(email: String): Boolean
}
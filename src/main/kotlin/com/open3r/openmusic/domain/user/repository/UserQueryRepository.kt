package com.open3r.openmusic.domain.user.repository

import com.open3r.openmusic.domain.user.domain.entity.UserEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface UserQueryRepository {
    fun getUsers(): List<UserEntity>
    fun getDeletedUsers(): List<UserEntity>
    fun searchUsers(keyword: String, pageable: Pageable): Page<UserEntity>
}
package com.open3r.openmusic.global.security

import com.open3r.openmusic.domain.user.domain.UserEntity

interface UserSecurity {
    val user: UserEntity
    val isAuthenticated: Boolean
}
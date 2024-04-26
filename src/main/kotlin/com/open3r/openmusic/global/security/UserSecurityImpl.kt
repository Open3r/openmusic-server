package com.open3r.openmusic.global.security

import com.open3r.openmusic.domain.user.domain.User
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class UserSecurityImpl : UserSecurity {
    override val user: User
        get() = (SecurityContextHolder.getContext().authentication.principal as CustomUserDetails).user
}
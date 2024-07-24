package com.open3r.openmusic.global.security

import com.open3r.openmusic.domain.user.domain.UserEntity
import com.open3r.openmusic.domain.user.repository.UserRepository
import com.open3r.openmusic.global.error.CustomException
import com.open3r.openmusic.global.error.ErrorCode
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class UserSecurityImpl(
    private val userRepository: UserRepository
) : UserSecurity {
    override val user: UserEntity
        get() {
            val email = SecurityContextHolder.getContext().authentication.name

            return userRepository.findByEmail(email) ?: throw CustomException(ErrorCode.USER_NOT_FOUND)
        }

    override val isAuthenticated: Boolean
        get() = SecurityContextHolder.getContext().authentication.isAuthenticated
}
package com.open3r.openmusic.global.security.jwt.details

import com.open3r.openmusic.domain.user.repository.UserRepository
import com.open3r.openmusic.global.error.CustomException
import com.open3r.openmusic.global.error.ErrorCode
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class JwtUserDetailsService(
    private val userRepository: UserRepository
) : UserDetailsService {
    override fun loadUserByUsername(email: String): UserDetails {
        val user = userRepository.findByEmail(email) ?: throw CustomException(ErrorCode.USER_NOT_FOUND)

        return JwtUserDetails(user)
    }
}
package com.open3r.openmusic.domain.admin.user.service.impl

import com.open3r.openmusic.domain.admin.user.service.AdminUserService
import com.open3r.openmusic.domain.user.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class AdminUserServiceImpl(
    private val userRepository: UserRepository
) : AdminUserService {
    override fun deleteUser(userId: Long) {
        userRepository.deleteById(userId)
    }
}
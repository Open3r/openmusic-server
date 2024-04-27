package com.open3r.openmusic.domain.user.service

import com.open3r.openmusic.domain.user.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
    private val userRepository: UserRepository
)

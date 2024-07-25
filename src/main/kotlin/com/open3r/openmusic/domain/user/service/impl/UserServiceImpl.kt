package com.open3r.openmusic.domain.user.service.impl

import com.open3r.openmusic.domain.user.domain.UserRole
import com.open3r.openmusic.domain.user.dto.request.UserUpdateRequest
import com.open3r.openmusic.domain.user.dto.response.UserResponse
import com.open3r.openmusic.domain.user.repository.UserRepository
import com.open3r.openmusic.domain.user.service.UserService
import com.open3r.openmusic.global.error.CustomException
import com.open3r.openmusic.global.error.ErrorCode
import com.open3r.openmusic.global.security.UserSecurity
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val userSecurity: UserSecurity
) : UserService {
    @Transactional(readOnly = true)
    override fun getUsers(): List<UserResponse> {
        val users = userRepository.findAll()

        return users.map { UserResponse.of(it) }
    }

    @Transactional(readOnly = true)
    override fun getMe(): UserResponse {
        val user = userSecurity.user

        return UserResponse.of(user)
    }

    @Transactional
    override fun updateMe(request: UserUpdateRequest): UserResponse {
        val user = userSecurity.user

        user.nickname = request.nickname ?: user.nickname
        user.avatarUrl = request.avatarUrl ?: user.avatarUrl

        return UserResponse.of(userRepository.save(user))
    }

    @Transactional(readOnly = true)
    override fun getUser(userId: Long): UserResponse {
        val user = userRepository.findByIdOrNull(userId) ?: throw CustomException(ErrorCode.USER_NOT_FOUND)

        return UserResponse.of(user)
    }

    @Transactional
    override fun deleteUser(userId: Long) {
        val user = userRepository.findByIdOrNull(userId) ?: throw CustomException(ErrorCode.USER_NOT_FOUND)
        val current = userSecurity.user

        if (user.id != current.id && current.role != UserRole.ADMIN) throw CustomException(ErrorCode.USER_NOT_DELETABLE)

        userRepository.delete(user)
    }
}
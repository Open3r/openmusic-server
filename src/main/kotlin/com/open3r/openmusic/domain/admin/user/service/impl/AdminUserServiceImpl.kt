package com.open3r.openmusic.domain.admin.user.service.impl

import com.open3r.openmusic.domain.admin.user.service.AdminUserService
import com.open3r.openmusic.domain.user.domain.enums.UserStatus
import com.open3r.openmusic.domain.user.dto.response.UserResponse
import com.open3r.openmusic.domain.user.repository.UserQueryRepository
import com.open3r.openmusic.domain.user.repository.UserRepository
import com.open3r.openmusic.global.error.CustomException
import com.open3r.openmusic.global.error.ErrorCode
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AdminUserServiceImpl(
    private val userRepository: UserRepository,
    private val userQueryRepository: UserQueryRepository
) : AdminUserService {
    @Transactional(readOnly = true)
    override fun getUsers() = userQueryRepository.getUsers().map { UserResponse.of(it) }

    @Transactional
    override fun getDeletedUsers(): List<UserResponse> {
        return userQueryRepository.getDeletedUsers().map { UserResponse.of(it) }
    }

    @Transactional
    override fun deleteUser(userId: Long) {
        val user = userRepository.findByIdOrNull(userId) ?: throw CustomException(ErrorCode.USER_NOT_FOUND)

        if (user.status == UserStatus.DELETED) throw CustomException(ErrorCode.USER_ALREADY_DELETED)

        user.status = UserStatus.DELETED

        userRepository.save(user)
    }
}
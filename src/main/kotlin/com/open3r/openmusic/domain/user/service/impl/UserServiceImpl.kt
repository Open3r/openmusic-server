package com.open3r.openmusic.domain.user.service.impl

import com.open3r.openmusic.domain.song.domain.enums.SongGenre
import com.open3r.openmusic.domain.user.domain.enums.UserRole
import com.open3r.openmusic.domain.user.dto.request.UserUpdateRequest
import com.open3r.openmusic.domain.user.dto.response.CheckEmailResponse
import com.open3r.openmusic.domain.user.dto.response.UserResponse
import com.open3r.openmusic.domain.user.repository.UserRepository
import com.open3r.openmusic.domain.user.service.UserService
import com.open3r.openmusic.global.error.CustomException
import com.open3r.openmusic.global.error.ErrorCode
import com.open3r.openmusic.global.security.UserSecurity
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val userSecurity: UserSecurity,
    private val passwordEncoder: PasswordEncoder
) : UserService {
    @Transactional(readOnly = true)
    override fun getUsers(): List<UserResponse> {
        val users = userRepository.findAll()

        return users.map { UserResponse.of(it) }
    }

    @Transactional(readOnly = true)
    override fun checkEmail(email: String): CheckEmailResponse {
        return CheckEmailResponse(userRepository.existsByEmail(email))
    }

    @Transactional(readOnly = true)
    override fun getMe(): UserResponse {
        val user = userSecurity.user

        return UserResponse.of(user)
    }

    @Transactional
    override fun updateMe(request: UserUpdateRequest): UserResponse {
        var user = userSecurity.user

        if (!passwordEncoder.matches(request.currentPassword, user.password))
            throw CustomException(ErrorCode.USER_PASSWORD_NOT_MATCH)

        if (request.nickname != null) {
            user.nickname = request.nickname
        }

        if (request.avatarUrl != null) {
            user.avatarUrl = request.avatarUrl
        }

        if (request.password != null) {
            user.password = passwordEncoder.encode(request.password)
        }

        user = userRepository.save(user)

        return UserResponse.of(user)
    }

    @Transactional
    override fun addGenre(genre: SongGenre) {
        val user = userSecurity.user

        if (user.genres.contains(genre)) throw CustomException(ErrorCode.USER_GENRE_ALREADY_EXISTS)

        user.genres.add(genre)

        userRepository.save(user)
    }

    @Transactional
    override fun removeGenre(genre: SongGenre) {
        val user = userSecurity.user

        if (!user.genres.contains(genre)) throw CustomException(ErrorCode.USER_GENRE_NOT_FOUND)

        user.genres.remove(genre)

        userRepository.save(user)
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
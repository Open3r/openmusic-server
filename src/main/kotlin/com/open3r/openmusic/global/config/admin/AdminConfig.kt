package com.open3r.openmusic.global.config.admin

import com.open3r.openmusic.domain.user.domain.entity.UserEntity
import com.open3r.openmusic.domain.user.domain.enums.UserProvider
import com.open3r.openmusic.domain.user.domain.enums.UserRole
import com.open3r.openmusic.domain.user.repository.UserRepository
import jakarta.annotation.PostConstruct
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Configuration
class AdminConfig(
    private val adminProperties: AdminProperties,
    private val userRepository: UserRepository,
    private val passwordEncoder: BCryptPasswordEncoder
) {
    @PostConstruct
    fun init() {
        if (!userRepository.existsByEmail(adminProperties.email)) {
            val user = UserEntity(
                nickname = "어드민",
                email = adminProperties.email,
                password = passwordEncoder.encode(adminProperties.password),
                role = UserRole.ADMIN,
                provider = UserProvider.DEFAULT,
                avatarUrl = "https://t4.ftcdn.net/jpg/01/43/42/83/360_F_143428338_gcxw3Jcd0tJpkvvb53pfEztwtU9sxsgT.jpg",
                providerId = "-1"
            )

            userRepository.save(user)
        }

        if (!userRepository.existsByEmail("dev@openmusic.com")) {
            val user = UserEntity(
                nickname = "개발자",
                email = "dev@openmusic.com",
                password = passwordEncoder.encode("qwer1234"),
                role = UserRole.USER,
                provider = UserProvider.DEFAULT,
                avatarUrl = "https://t4.ftcdn.net/jpg/01/43/42/83/360_F_143428338_gcxw3Jcd0tJpkvvb53pfEztwtU9sxsgT.jpg",
                providerId = "-1"
            )

            userRepository.save(user)
        }
    }
}
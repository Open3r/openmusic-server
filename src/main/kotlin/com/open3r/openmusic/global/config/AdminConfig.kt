package com.open3r.openmusic.global.config

import com.open3r.openmusic.domain.user.domain.UserEntity
import com.open3r.openmusic.domain.user.domain.UserProvider
import com.open3r.openmusic.domain.user.domain.UserRole
import com.open3r.openmusic.domain.user.repository.UserRepository
import com.open3r.openmusic.global.properties.AdminProperties
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
        if (!userRepository.existsByEmail("admin@openmusic.com")) {
            val user = UserEntity(
                nickname = "admin",
                email = adminProperties.email,
                password = passwordEncoder.encode(adminProperties.password),
                role = UserRole.ADMIN,
                provider = UserProvider.DEFAULT,
                avatarUrl = "https://t4.ftcdn.net/jpg/01/43/42/83/360_F_143428338_gcxw3Jcd0tJpkvvb53pfEztwtU9sxsgT.jpg",
                providerId = "-1"
            )

            userRepository.save(user)
        }
    }
}
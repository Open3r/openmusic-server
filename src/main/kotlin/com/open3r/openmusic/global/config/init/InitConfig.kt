package com.open3r.openmusic.global.config.init

import com.open3r.openmusic.domain.album.repository.AlbumQueryRepository
import com.open3r.openmusic.domain.song.repository.SongQueryRepository
import com.open3r.openmusic.domain.user.domain.entity.UserEntity
import com.open3r.openmusic.domain.user.domain.enums.UserRole
import com.open3r.openmusic.domain.user.repository.UserRepository
import com.open3r.openmusic.logger
import jakarta.annotation.PostConstruct
import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.PageRequest
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
class InitConfig(
    private val albumQueryRepository: AlbumQueryRepository,
    private val playlistQueryRepository: AlbumQueryRepository,
    private val songQueryRepository: SongQueryRepository,
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
) {
    @PostConstruct
    fun init() {
        albumQueryRepository.getAlbums(PageRequest.of(0, 10))
        playlistQueryRepository.getAlbums(PageRequest.of(0, 10))
        songQueryRepository.getSongs(PageRequest.of(0, 10))

        for (i in 1..10) {
            if (!userRepository.existsByEmail("user${i}@openmusic.com")) {
                userRepository.save(
                    UserEntity(
                        nickname = "유저-$i",
                        email = "user${i}@openmusic.com",
                        password = passwordEncoder.encode("qwer1234!!"),
                        role = UserRole.USER,
                        avatarUrl = "https://t4.ftcdn.net/jpg/01/43/42/83/360_F_143428338_gcxw3Jcd0tJpkvvb53pfEztwtU9sxsgT.jpg"
                    )
                )

                logger().info("User $i created")
            }
        }
    }
}
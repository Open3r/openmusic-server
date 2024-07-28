package com.open3r.openmusic.domain.user.domain.entity

import com.open3r.openmusic.domain.song.domain.enums.SongGenre
import com.open3r.openmusic.domain.user.domain.enums.UserProvider
import com.open3r.openmusic.domain.user.domain.enums.UserRole
import com.open3r.openmusic.domain.user.domain.enums.UserStatus
import com.open3r.openmusic.global.common.domain.entity.BaseEntity
import jakarta.persistence.*

@Entity
@Table(name = "users")
class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "nickname", nullable = false)
    var nickname: String,

    @Column(name = "email", nullable = false, unique = true)
    val email: String,

    @Column(name = "password", nullable = false)
    var password: String,

    @Column(name = "avatar_url", nullable = false)
    var avatarUrl: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "provider", nullable = false, updatable = false)
    val provider: UserProvider,

    @Column(name = "provider_id", nullable = false, updatable = false)
    val providerId: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    var role: UserRole,

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    var status: UserStatus = UserStatus.ACTIVE,

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_genres", joinColumns = [JoinColumn(name = "user_id")])
    @Column(name = "genre", nullable = false)
    val genres: MutableSet<SongGenre> = mutableSetOf(),
) : BaseEntity()
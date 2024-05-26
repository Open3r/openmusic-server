package com.open3r.openmusic.domain.user.domain

import com.open3r.openmusic.global.common.BaseEntity
import jakarta.persistence.*

@Entity
@Table(name = "users")
class User(
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

    @Column(name = "verified", nullable = false)
    var verified: Boolean = false,

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    var role: UserRole,

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    var status: UserStatus = UserStatus.ACTIVE
) : BaseEntity()
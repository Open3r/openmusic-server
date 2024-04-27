package com.open3r.openmusic.domain.user.domain

import com.open3r.openmusic.global.common.BaseEntity
import jakarta.persistence.*

@Entity
@Table(name = "users")
class User(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val username: String,
    val password: String,

    @Enumerated(EnumType.STRING)
    val role: UserRole
): BaseEntity()
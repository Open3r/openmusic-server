package com.open3r.openmusic.domain.user.domain

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
)
package com.open3r.openmusic.domain.song.domain

import com.open3r.openmusic.domain.user.domain.User
import jakarta.persistence.*

@Entity
@Table(name = "songs")
class Song(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,
)
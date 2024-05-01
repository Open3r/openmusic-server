package com.open3r.openmusic.domain.playlist.domain

import com.open3r.openmusic.domain.user.domain.User
import jakarta.persistence.*

@Entity
@Table(name = "playlists")
class Playlist(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,
)
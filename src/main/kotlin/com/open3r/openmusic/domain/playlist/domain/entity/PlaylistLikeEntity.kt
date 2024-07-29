package com.open3r.openmusic.domain.playlist.domain.entity

import com.open3r.openmusic.domain.user.domain.entity.UserEntity
import jakarta.persistence.*

@Entity
@Table(name = "playlist_likes")
class PlaylistLikeEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "playlist_id", nullable = false)
    val playlist: PlaylistEntity,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: UserEntity,
)
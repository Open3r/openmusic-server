package com.open3r.openmusic.domain.album.domain.entity

import com.open3r.openmusic.domain.user.domain.entity.UserEntity
import jakarta.persistence.*

@Entity
@Table(name = "album_likes")
class AlbumLikeEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "album_id", nullable = false)
    val album: AlbumEntity,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: UserEntity,
)
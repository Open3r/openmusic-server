package com.open3r.openmusic.domain.song.domain.entity

import com.open3r.openmusic.domain.user.domain.entity.UserEntity
import jakarta.persistence.*

@Entity
@Table(name = "song_likes")
class SongLikeEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "song_id", nullable = false)
    val song: SongEntity,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: UserEntity,
)
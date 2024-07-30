package com.open3r.openmusic.domain.user.domain.entity

import com.open3r.openmusic.domain.song.domain.entity.SongEntity
import jakarta.persistence.*

@Entity
@Table(name = "user_recents")
class UserRecentEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: UserEntity,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "song_id", nullable = false)
    val song: SongEntity,
)
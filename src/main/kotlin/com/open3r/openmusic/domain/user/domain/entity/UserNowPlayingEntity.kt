package com.open3r.openmusic.domain.user.domain.entity

import com.open3r.openmusic.domain.song.domain.entity.SongEntity
import jakarta.persistence.*

@Entity
@Table(name = "user_now_playing")
class UserNowPlayingEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: UserEntity,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "song_id", nullable = false)
    val song: SongEntity,

    @Column(name = "progress", nullable = false)
    val progress: Long
)
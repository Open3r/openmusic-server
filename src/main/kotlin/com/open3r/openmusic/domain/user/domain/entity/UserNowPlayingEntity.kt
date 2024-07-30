package com.open3r.openmusic.domain.user.domain.entity

import com.open3r.openmusic.domain.song.domain.entity.SongEntity
import jakarta.persistence.*

@Entity
@Table(name = "user_now_playing")
class UserNowPlayingEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    var user: UserEntity,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "song_id", nullable = false)
    var song: SongEntity,

    @Column(name = "progress", nullable = false)
    var progress: Long
)
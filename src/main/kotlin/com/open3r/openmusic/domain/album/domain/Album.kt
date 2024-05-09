package com.open3r.openmusic.domain.album.domain

import com.open3r.openmusic.domain.song.domain.Song
import com.open3r.openmusic.domain.user.domain.User
import com.open3r.openmusic.global.common.BaseEntity
import jakarta.persistence.*
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction

@Entity
@Table(name = "albums")
class Album(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val title: String,
    val description: String,

    @OneToMany(fetch = FetchType.LAZY)
    val songs: MutableList<Song> = mutableListOf(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    val user: User,
) : BaseEntity()
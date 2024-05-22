package com.open3r.openmusic.domain.album.domain

import com.open3r.openmusic.domain.song.domain.Song
import com.open3r.openmusic.domain.user.domain.User
import jakarta.persistence.*

@Entity
@Table(name = "albums")
class Album(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "title", nullable = false)
    val title: String,

    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val songs: MutableList<Song> = mutableListOf(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: User
)
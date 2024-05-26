package com.open3r.openmusic.domain.playlist.domain

import com.open3r.openmusic.domain.song.domain.Song
import com.open3r.openmusic.domain.user.domain.User
import jakarta.persistence.*
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction

@Entity
@Table(name = "playlists")
class Playlist(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "title", nullable = true)
    var title: String,

    @Column(name = "cover_url", nullable = true)
    var coverUrl: String,

    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
    val songs: MutableList<Song> = mutableListOf(),

    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
    val likes: MutableList<User> = mutableListOf(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artist_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    val artist: User
)
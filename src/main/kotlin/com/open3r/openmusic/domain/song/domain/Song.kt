package com.open3r.openmusic.domain.song.domain

import com.open3r.openmusic.domain.album.domain.Album
import com.open3r.openmusic.domain.user.domain.User
import com.open3r.openmusic.global.common.BaseEntity
import jakarta.persistence.*
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction

@Entity
@Table(name = "songs")
class Song(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "title", nullable = false)
    var title: String,

    @Column(name = "description", nullable = false)
    var description: String,

    @Column(name = "url", nullable = false)
    var url: String,

    @Column(name = "cover_url", nullable = false)
    var coverUrl: String,

    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    val likes: MutableList<User> = mutableListOf(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "album_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    val album: Album,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artist_id", nullable = false)
    val artist: User
) : BaseEntity()
package com.open3r.openmusic.domain.song.domain.entity

import com.open3r.openmusic.domain.album.domain.entity.AlbumEntity
import com.open3r.openmusic.domain.user.domain.User
import com.open3r.openmusic.global.common.BaseEntity
import jakarta.persistence.*

@Entity
@Table(name = "songs")
class SongEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "title", nullable = false)
    var title: String,

    @Column(name = "url", nullable = false)
    var url: String,

    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    val likes: MutableList<User> = mutableListOf(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "album_id", nullable = false)
    val album: AlbumEntity,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artist_id", nullable = false)
    val artist: User
) : BaseEntity()
package com.open3r.openmusic.domain.album.domain.entity

import com.open3r.openmusic.domain.album.domain.enums.AlbumGenre
import com.open3r.openmusic.domain.album.domain.enums.AlbumScope
import com.open3r.openmusic.domain.song.domain.entity.SongEntity
import com.open3r.openmusic.domain.user.domain.UserEntity
import com.open3r.openmusic.global.common.BaseEntity
import jakarta.persistence.*

@Entity
@Table(name = "albums")
class AlbumEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "title", nullable = true)
    var title: String,

    @Column(name = "cover_url", nullable = true)
    var coverUrl: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "scope", nullable = true)
    var scope: AlbumScope,

    @Enumerated(EnumType.STRING)
    @Column(name = "genre", nullable = true)
    var genre: AlbumGenre,

    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val likes: MutableList<UserEntity> = mutableListOf(),

    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val songs: MutableList<SongEntity> = mutableListOf(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artist_id", nullable = false)
    val artist: UserEntity,
): BaseEntity()
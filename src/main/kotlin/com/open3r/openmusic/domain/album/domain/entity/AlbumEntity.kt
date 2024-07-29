package com.open3r.openmusic.domain.album.domain.entity

import com.open3r.openmusic.domain.album.domain.enums.AlbumScope
import com.open3r.openmusic.domain.song.domain.enums.SongGenre
import com.open3r.openmusic.domain.user.domain.entity.UserEntity
import com.open3r.openmusic.global.common.domain.entity.BaseEntity
import jakarta.persistence.*

@Entity
@Table(name = "albums")
class AlbumEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "title", nullable = true)
    var title: String,

    @Column(name = "description", nullable = true)
    var description: String,

    @Column(name = "cover_url", nullable = true)
    var coverUrl: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "scope", nullable = true)
    var scope: AlbumScope,

    @Enumerated(EnumType.STRING)
    @Column(name = "genre", nullable = true)
    var genre: SongGenre,

    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY, orphanRemoval = true)
    val likes: MutableList<AlbumLikeEntity> = mutableListOf(),

    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY, orphanRemoval = true)
    val songs: MutableList<AlbumSongEntity> = mutableListOf(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artist_id", nullable = false)
    val artist: UserEntity,
) : BaseEntity()
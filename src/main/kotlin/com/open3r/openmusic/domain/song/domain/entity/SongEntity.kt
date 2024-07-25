package com.open3r.openmusic.domain.song.domain.entity

import com.open3r.openmusic.domain.album.domain.entity.AlbumEntity
import com.open3r.openmusic.domain.song.domain.enums.SongGenre
import com.open3r.openmusic.domain.album.domain.enums.AlbumScope
import com.open3r.openmusic.domain.user.domain.entity.UserEntity
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

    @Column(name = "url", nullable = false, columnDefinition = "longtext")
    var url: String,

    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    val likes: MutableList<UserEntity> = mutableListOf(),

    @Enumerated(EnumType.STRING)
    @Column(name = "scope", nullable = false)
    var scope: AlbumScope,

    @Enumerated(EnumType.STRING)
    @Column(name = "genre", nullable = false)
    var genre: SongGenre,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "album_id", nullable = false)
    val album: AlbumEntity,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artist_id", nullable = false)
    val artist: UserEntity
) : BaseEntity()
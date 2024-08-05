package com.open3r.openmusic.domain.song.domain.entity

import com.open3r.openmusic.domain.album.domain.entity.AlbumEntity
import com.open3r.openmusic.domain.album.domain.enums.AlbumScope
import com.open3r.openmusic.domain.album.domain.enums.AlbumStatus
import com.open3r.openmusic.domain.song.domain.enums.SongGenre
import com.open3r.openmusic.domain.user.domain.entity.UserEntity
import com.open3r.openmusic.global.common.domain.entity.BaseEntity
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

    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true, mappedBy = "song")
    val likes: MutableList<SongLikeEntity> = mutableListOf(),

    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true, mappedBy = "song")
    val lyrics: MutableList<SongLyricsEntity> = mutableListOf(),

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    var status: AlbumStatus,

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
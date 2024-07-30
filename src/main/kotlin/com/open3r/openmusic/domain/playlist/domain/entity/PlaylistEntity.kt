package com.open3r.openmusic.domain.playlist.domain.entity

import com.open3r.openmusic.domain.playlist.domain.enums.PlaylistScope
import com.open3r.openmusic.domain.user.domain.entity.UserEntity
import com.open3r.openmusic.global.common.domain.entity.BaseEntity
import jakarta.persistence.*
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction

@Entity
@Table(name = "playlists")
class PlaylistEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "title", nullable = true)
    var title: String,

    @Column(name = "cover_url", nullable = true)
    var coverUrl: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "scope", nullable = false)
    var scope: PlaylistScope,

    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true, mappedBy = "playlist")
    val songs: MutableList<PlaylistSongEntity> = mutableListOf(),

    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true, mappedBy = "playlist")
    val likes: MutableList<PlaylistLikeEntity> = mutableListOf(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artist_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    val artist: UserEntity
) : BaseEntity()
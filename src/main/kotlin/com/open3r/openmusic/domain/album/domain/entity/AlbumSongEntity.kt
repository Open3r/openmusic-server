package com.open3r.openmusic.domain.album.domain.entity

import com.open3r.openmusic.domain.song.domain.entity.SongEntity
import jakarta.persistence.*

@Entity
@Table(name = "album_songs")
class AlbumSongEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "album_id", nullable = false)
    val album: AlbumEntity,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "song_id", nullable = false)
    val song: SongEntity,
)
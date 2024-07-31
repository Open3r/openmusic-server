package com.open3r.openmusic.domain.song.domain.entity

import jakarta.persistence.*

@Entity
@Table(name = "song_lyrics")
class SongLyricsEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "song_id", nullable = false)
    val song: SongEntity,

    @Column(name = "lyrics", nullable = false)
    val lyrics: String,

    @Column(name = "timestamp", nullable = false)
    val timestamp: Int
)
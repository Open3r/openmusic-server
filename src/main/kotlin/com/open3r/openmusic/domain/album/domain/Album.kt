package com.open3r.openmusic.domain.album.domain

import com.open3r.openmusic.domain.song.domain.Song
import com.open3r.openmusic.global.common.BaseEntity
import jakarta.persistence.*

@Entity
@Table(name = "albums")
class Album(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val title: String,
    val description: String,

    @OneToMany(mappedBy = "album", fetch = FetchType.LAZY)
    val songs: MutableList<Song> = mutableListOf(),
) : BaseEntity()
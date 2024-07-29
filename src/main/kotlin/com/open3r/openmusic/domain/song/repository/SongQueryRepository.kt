package com.open3r.openmusic.domain.song.repository

import com.open3r.openmusic.domain.song.domain.entity.SongEntity
import com.open3r.openmusic.domain.song.domain.enums.SongGenre
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice

interface SongQueryRepository {
    fun getSongs(pageable: Pageable): Page<SongEntity>
    fun getRankingSongs(pageable: Pageable): Slice<SongEntity>
    fun getGenreSongs(genre: SongGenre, pageable: Pageable): Page<SongEntity>
    fun getSongsByGenreIn(genres: List<SongGenre>, pageable: Pageable): Page<SongEntity>
}
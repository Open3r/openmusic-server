package com.open3r.openmusic.domain.song.repository

import com.open3r.openmusic.domain.song.domain.entity.SongEntity
import com.open3r.openmusic.domain.song.domain.enums.SongGenre
import com.open3r.openmusic.domain.user.domain.entity.UserEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice

interface SongQueryRepository {
    fun getSongs(pageable: Pageable): Page<SongEntity>
    fun getSongsByArtist(artist: UserEntity): List<SongEntity>
    fun getMySongs(pageable: Pageable): Page<SongEntity>

    fun getRankingSongs(pageable: Pageable): Slice<SongEntity>
    fun getLatestSongs(pageable: Pageable): Slice<SongEntity>
    fun getGenreSongs(genre: SongGenre, pageable: Pageable): Page<SongEntity>
    fun getSongsByGenreIn(genres: List<SongGenre>, pageable: Pageable): Page<SongEntity>

    fun searchSongs(query: String, pageable: Pageable): Slice<SongEntity>
}
package com.open3r.openmusic.domain.album.dto.request

import com.open3r.openmusic.domain.album.domain.enums.AlbumGenre
import com.open3r.openmusic.domain.album.domain.enums.AlbumScope
import com.open3r.openmusic.domain.song.dto.request.SongCreateRequest
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.hibernate.validator.constraints.URL

data class AlbumCreateRequest(
    @field:NotBlank
    val title: String,
    val description: String,
    @field:NotBlank
    @field:URL
    val coverUrl: String,
    val artist: Long,
    @field:NotNull
    val scope: AlbumScope,
    @field:NotNull
    val genre: AlbumGenre,
    val songs: List<SongCreateRequest>
)
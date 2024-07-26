package com.open3r.openmusic.domain.album.dto.request

import com.open3r.openmusic.domain.album.domain.enums.AlbumScope
import com.open3r.openmusic.domain.song.domain.enums.SongGenre
import com.open3r.openmusic.domain.song.dto.request.SongCreateRequest
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.hibernate.validator.constraints.URL

data class AlbumCreateRequest(
    @field:NotBlank
    val title: String,
    @field:NotBlank
    val description: String,
    @field:NotBlank
    @field:URL
    val coverUrl: String,
    @field:NotNull
    val scope: AlbumScope,
    @field:NotNull
    val genre: SongGenre,
    val songs: List<SongCreateRequest>
)
package com.open3r.openmusic.domain.album.dto.request

import com.open3r.openmusic.domain.album.domain.enums.AlbumGenre
import com.open3r.openmusic.domain.album.domain.enums.AlbumScope
import com.open3r.openmusic.domain.song.dto.request.SongCreateRequest
import jakarta.validation.constraints.NotBlank
import org.hibernate.validator.constraints.URL

data class AlbumCreateRequest(
    @field:NotBlank
    val title: String,
    @field:NotBlank
    @field:URL
    val coverUrl: String,
    val artist: Long,
    val scope: AlbumScope,
    val genre: AlbumGenre,
    val songs: List<SongCreateRequest>
)
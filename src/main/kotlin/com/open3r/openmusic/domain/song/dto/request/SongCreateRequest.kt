package com.open3r.openmusic.domain.song.dto.request

import jakarta.validation.constraints.NotBlank
import org.hibernate.validator.constraints.URL

data class SongCreateRequest(
    @field:NotBlank
    val title: String,
    @field:NotBlank
    @field:URL
    val url: String,
    val lyrics: List<SongCreateLyricsRequest>? = null,
)
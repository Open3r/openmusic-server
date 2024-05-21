package com.open3r.openmusic.domain.song.dto.request

import com.open3r.openmusic.domain.song.domain.Song
import com.open3r.openmusic.domain.user.domain.User
import jakarta.validation.constraints.NotBlank
import org.hibernate.validator.constraints.URL

data class SongCreateRequest(
    @field:NotBlank
    val title: String,
    @field:NotBlank
    val description: String,
    @field:URL
    val url: String,
) {
    fun toEntity(artist: User) = Song(
        title = title,
        description = description,
        url = url,
        artist = artist
    )
}
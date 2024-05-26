package com.open3r.openmusic.domain.album.dto.request

import com.open3r.openmusic.domain.album.domain.Album
import com.open3r.openmusic.domain.user.domain.User
import jakarta.validation.constraints.NotBlank
import org.hibernate.validator.constraints.URL

data class AlbumCreateRequest(
    @field:NotBlank
    val title: String,
    @field:NotBlank
    @field:URL
    val coverUrl: String,
) {
    fun toEntity(user: User) = Album(
        title = title,
        coverUrl = coverUrl,
        artist = user
    )
}
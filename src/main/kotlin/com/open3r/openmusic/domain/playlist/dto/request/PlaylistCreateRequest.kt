package com.open3r.openmusic.domain.playlist.dto.request

import com.open3r.openmusic.domain.playlist.domain.entity.PlaylistEntity
import com.open3r.openmusic.domain.playlist.domain.enums.PlaylistScope
import com.open3r.openmusic.domain.user.domain.entity.UserEntity
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.hibernate.validator.constraints.URL

data class PlaylistCreateRequest(
    @field:NotBlank
    val title: String,
    @field:NotBlank
    @field:URL
    val coverUrl: String,
    @field:NotNull
    val scope: PlaylistScope
) {
    fun toEntity(user: UserEntity) = PlaylistEntity(
        title = title,
        coverUrl = coverUrl,
        scope = scope,
        artist = user
    )
}
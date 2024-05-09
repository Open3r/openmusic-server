package com.open3r.openmusic.domain.album.dto.request

import com.open3r.openmusic.domain.album.domain.Album

data class AlbumCreateRequest(
    val title: String,
    val description: String,
) {
    fun toEntity() = Album(
        title = title,
        description = description,

        )
}
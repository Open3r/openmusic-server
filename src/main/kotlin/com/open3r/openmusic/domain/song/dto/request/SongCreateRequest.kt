package com.open3r.openmusic.domain.song.dto.request

import com.open3r.openmusic.domain.album.domain.Album
import com.open3r.openmusic.domain.song.domain.Song
import com.open3r.openmusic.domain.user.domain.User
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Positive
import org.hibernate.validator.constraints.URL

data class SongCreateRequest(
    @field:NotBlank
    val title: String,
    @field:NotBlank
    val description: String,
    @field:NotBlank
    @field:URL
    val coverUrl: String,
    @field:URL
    val url: String,
    @field:Positive
    val albumId: Long

) {
    fun toEntity(album: Album, artist: User) = Song(
        title = title,
        description = description,
        coverUrl = coverUrl,
        url = url,
        album = album,
        artist = artist
    )
}
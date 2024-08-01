package com.open3r.openmusic.domain.banner.dto.response

import com.open3r.openmusic.domain.banner.domain.entity.BannerEntity
import java.time.LocalDateTime

data class BannerResponse(
    val id: Long,
    val url: String,
    val imageUrl: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
) {
    companion object {
        fun of(banner: BannerEntity) = BannerResponse(
            id = banner.id!!,
            url = banner.url,
            imageUrl = banner.imageUrl,
            createdAt = banner.createdAt!!,
            updatedAt = banner.updatedAt!!,
        )
    }
}
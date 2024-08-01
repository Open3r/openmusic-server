package com.open3r.openmusic.domain.admin.banner.dto.request

import jakarta.validation.constraints.NotBlank
import org.hibernate.validator.constraints.URL

data class BannerCreateRequest(
    @field:NotBlank
    @field:URL
    val url: String,
    @field:NotBlank
    @field:URL
    val imageUrl: String,
)
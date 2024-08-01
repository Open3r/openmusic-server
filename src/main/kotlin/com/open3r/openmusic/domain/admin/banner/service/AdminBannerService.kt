package com.open3r.openmusic.domain.admin.banner.service

import com.open3r.openmusic.domain.admin.banner.dto.request.BannerCreateRequest
import com.open3r.openmusic.domain.banner.dto.response.BannerResponse

interface AdminBannerService {
    fun createBanner(request: BannerCreateRequest): BannerResponse
    fun deleteBanner(bannerId: Long)
}
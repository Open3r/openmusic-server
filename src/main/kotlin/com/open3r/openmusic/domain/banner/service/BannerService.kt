package com.open3r.openmusic.domain.banner.service

import com.open3r.openmusic.domain.banner.dto.response.BannerResponse

interface BannerService {
    fun getBanners(): List<BannerResponse>
    fun getBanner(bannerId: Long): BannerResponse
}
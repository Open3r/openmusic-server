package com.open3r.openmusic.domain.admin.banner.service.impl

import com.open3r.openmusic.domain.admin.banner.dto.request.BannerCreateRequest
import com.open3r.openmusic.domain.admin.banner.service.AdminBannerService
import com.open3r.openmusic.domain.banner.domain.entity.BannerEntity
import com.open3r.openmusic.domain.banner.dto.response.BannerResponse
import com.open3r.openmusic.domain.banner.repository.BannerRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AdminBannerServiceImpl(
    private val bannerRepository: BannerRepository
) : AdminBannerService {
    @Transactional
    override fun createBanner(request: BannerCreateRequest): BannerResponse {
        return BannerResponse.of(bannerRepository.save(BannerEntity(url = request.url, imageUrl = request.imageUrl)))
    }

    @Transactional
    override fun deleteBanner(bannerId: Long) {
        bannerRepository.deleteById(bannerId)
    }
}
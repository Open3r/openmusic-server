package com.open3r.openmusic.domain.banner.service.impl

import com.open3r.openmusic.domain.banner.dto.response.BannerResponse
import com.open3r.openmusic.domain.banner.repository.BannerRepository
import com.open3r.openmusic.domain.banner.service.BannerService
import com.open3r.openmusic.global.error.CustomException
import com.open3r.openmusic.global.error.ErrorCode
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BannerServiceImpl(
    private val bannerRepository: BannerRepository
) : BannerService {
    @Transactional(readOnly = true)
    override fun getBanners(): List<BannerResponse> {
        return bannerRepository.findAll().map { BannerResponse.of(it) }
    }

    @Transactional(readOnly = true)
    override fun getBanner(bannerId: Long): BannerResponse {
        return BannerResponse.of(
            bannerRepository.findByIdOrNull(bannerId) ?: throw CustomException(ErrorCode.BANNER_NOT_FOUND)
        )
    }
}
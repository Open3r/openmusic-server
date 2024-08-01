package com.open3r.openmusic.domain.banner.controller

import com.open3r.openmusic.domain.banner.service.BannerService
import com.open3r.openmusic.global.common.dto.response.BaseResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "배너", description = "Banner")
@RestController
@RequestMapping("/banners")
class BannerController(
    private val bannerService: BannerService
) {
    @Operation(summary = "배너 목록 조회")
    @GetMapping
    fun getBanners() = BaseResponse(bannerService.getBanners(), 200).toEntity()

    @Operation(summary = "배너 조회")
    @GetMapping("/{bannerId}")
    fun getBanner(@PathVariable bannerId: Long) = BaseResponse(bannerService.getBanner(bannerId), 200).toEntity()
}
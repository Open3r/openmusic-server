package com.open3r.openmusic.domain.admin.banner.controller

import com.open3r.openmusic.domain.admin.banner.dto.request.BannerCreateRequest
import com.open3r.openmusic.domain.admin.banner.service.AdminBannerService
import com.open3r.openmusic.global.common.dto.response.BaseResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@Tag(name = "관리자: 배너", description = "Admin: Banner")
@RestController
@RequestMapping("/admin/banners")
class AdminBannerController(
    private val adminBannerService: AdminBannerService
) {
    @Operation(summary = "배너 생성")
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    fun createBanner(@RequestBody request: BannerCreateRequest) =
        BaseResponse(adminBannerService.createBanner(request), 201).toEntity()

    @Operation(summary = "배너 삭제")
    @DeleteMapping("/{bannerId}")
    @PreAuthorize("hasRole('ADMIN')")
    fun deleteBanner(@PathVariable bannerId: Long) =
        BaseResponse(adminBannerService.deleteBanner(bannerId), 204).toEntity()
}
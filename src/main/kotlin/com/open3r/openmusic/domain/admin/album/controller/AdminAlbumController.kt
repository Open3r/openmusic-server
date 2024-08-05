package com.open3r.openmusic.domain.admin.album.controller

import com.open3r.openmusic.domain.admin.album.service.AdminAlbumService
import com.open3r.openmusic.global.common.dto.response.BaseResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@Tag(name = "어드민: 앨범", description = "Admin: Album")
@RestController
@RequestMapping("/admin/albums")
class AdminAlbumController(
    private val adminAlbumService: AdminAlbumService
) {
    @Operation(summary = "대기 중인 앨범 목록 조회", description = "대기 중인 앨범 목록을 조회합니다.")
    @GetMapping("/pending")
    @PreAuthorize("hasRole('ADMIN')")
    fun getPendingAlbums(@PageableDefault pageable: Pageable) =
        BaseResponse(adminAlbumService.getPendingAlbums(pageable), 200).toEntity()

    @Operation(summary = "승인된 앨범 목록 조회", description = "승인된 앨범 목록을 조회합니다.")
    @GetMapping("/approved")
    @PreAuthorize("hasRole('ADMIN')")
    fun getApprovedAlbums(@PageableDefault pageable: Pageable) =
        BaseResponse(adminAlbumService.getApprovedAlbums(pageable), 200).toEntity()

    @Operation(summary = "거부된 앨범 목록 조회", description = "거부된 앨범 목록을 조회합니다.")
    @GetMapping("/rejected")
    @PreAuthorize("hasRole('ADMIN')")
    fun getRejectedAlbums(@PageableDefault pageable: Pageable) =
        BaseResponse(adminAlbumService.getRejectedAlbums(pageable), 200).toEntity()

    @Operation(summary = "삭제된 앨범 목록 조회", description = "삭제된 앨범 목록을 조회합니다.")
    @GetMapping("/deleted")
    @PreAuthorize("hasRole('ADMIN')")
    fun getDeletedAlbums(@PageableDefault pageable: Pageable) =
        BaseResponse(adminAlbumService.getDeletedAlbums(pageable), 200).toEntity()

    @Operation(summary = "앨범 승인", description = "앨범을 승인합니다.")
    @PostMapping("/approve")
    @PreAuthorize("hasRole('ADMIN')")
    fun approveAlbum(@RequestParam albumId: Long) =
        BaseResponse(adminAlbumService.approveAlbum(albumId), 200).toEntity()

    @Operation(summary = "앨범 거부", description = "앨범을 거부합니다.")
    @PostMapping("/reject")
    @PreAuthorize("hasRole('ADMIN')")
    fun rejectAlbum(@RequestParam albumId: Long) = BaseResponse(adminAlbumService.rejectAlbum(albumId), 200).toEntity()
}
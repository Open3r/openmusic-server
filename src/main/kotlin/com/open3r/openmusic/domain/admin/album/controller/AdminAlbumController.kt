package com.open3r.openmusic.domain.admin.album.controller

import com.open3r.openmusic.domain.admin.album.service.AdminAlbumService
import com.open3r.openmusic.global.common.dto.response.BaseResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "어드민: 앨범", description = "Admin: Album")
@RestController
@RequestMapping("/admin/albums")
class AdminAlbumController(
    private val adminAlbumService: AdminAlbumService
) {
    @Operation(summary = "앨범 삭제", description = "앨범을 삭제합니다.")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{albumId}")
    fun deleteAlbum(@PathVariable albumId: Long) = BaseResponse(adminAlbumService.deleteAlbum(albumId), 204).toEntity()
}
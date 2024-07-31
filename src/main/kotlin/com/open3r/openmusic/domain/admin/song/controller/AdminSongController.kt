package com.open3r.openmusic.domain.admin.song.controller

import com.open3r.openmusic.domain.admin.song.service.AdminSongService
import com.open3r.openmusic.global.common.dto.response.BaseResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@Tag(name = "관리자: 음악", description = "Admin: Song")
@RestController
@RequestMapping("/admin/songs")
class AdminSongController(
    private val adminSongService: AdminSongService
) {
    @Operation(summary = "음악 목록")
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    fun getSongs() = BaseResponse(adminSongService.getSongs(), 200).toEntity()

    @Operation(summary = "음악 삭제")
    @DeleteMapping("/{songId}")
    @PreAuthorize("hasRole('ADMIN')")
    fun deleteSong(@PathVariable songId: Long) = BaseResponse(adminSongService.deleteSong(songId), 204).toEntity()
}
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

    @Operation(summary = "대기중인 음악 목록")
    @GetMapping("/pending")
    @PreAuthorize("hasRole('ADMIN')")
    fun getPendingSongs() = BaseResponse(adminSongService.getPendingSongs(), 200).toEntity()

    @Operation(summary = "승인된 음악 목록")
    @GetMapping("/approved")
    @PreAuthorize("hasRole('ADMIN')")
    fun getApprovedSongs() = BaseResponse(adminSongService.getApprovedSongs(), 200).toEntity()

    @Operation(summary = "거부된 음악 목록")
    @GetMapping("/rejected")
    @PreAuthorize("hasRole('ADMIN')")
    fun getRejectedSongs() = BaseResponse(adminSongService.getRejectedSongs(), 200).toEntity()

    @Operation(summary = "삭제된 음악 목록")
    @GetMapping("/deleted")
    @PreAuthorize("hasRole('ADMIN')")
    fun getDeletedSongs() = BaseResponse(adminSongService.getDeletedSongs(), 200).toEntity()

    @Operation(summary = "음악 승인")
    @PutMapping("/{songId}/approve")
    @PreAuthorize("hasRole('ADMIN')")
    fun approveSong(@PathVariable songId: Long) = BaseResponse(adminSongService.approveSong(songId), 200).toEntity()

    @Operation(summary = "음악 거부")
    @PutMapping("/{songId}/reject")
    @PreAuthorize("hasRole('ADMIN')")
    fun rejectSong(@PathVariable songId: Long) = BaseResponse(adminSongService.rejectSong(songId), 200).toEntity()

    @Operation(summary = "음악 삭제")
    @DeleteMapping("/{songId}")
    @PreAuthorize("hasRole('ADMIN')")
    fun deleteSong(@PathVariable songId: Long) = BaseResponse(adminSongService.deleteSong(songId), 204).toEntity()
}
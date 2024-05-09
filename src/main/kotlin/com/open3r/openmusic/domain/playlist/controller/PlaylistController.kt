package com.open3r.openmusic.domain.playlist.controller

import com.open3r.openmusic.domain.playlist.dto.request.PlaylistCreateRequest
import com.open3r.openmusic.domain.playlist.dto.request.PlaylistUpdateRequest
import com.open3r.openmusic.domain.playlist.dto.response.PlaylistResponse
import com.open3r.openmusic.domain.playlist.service.PlaylistService
import com.open3r.openmusic.global.common.BaseResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@Tag(name = "플레이리스트", description = "Playlist")
@RestController
@RequestMapping("/playlists")
class PlaylistController(
    private val playlistService: PlaylistService
) {
    @Operation(summary = "플레이리스트 목록 조회")
    @GetMapping
    fun getPlaylists(): ResponseEntity<BaseResponse<List<PlaylistResponse>>> {
        return BaseResponse(playlistService.getPlaylists(), 200).toEntity()
    }

    @Operation(summary = "플레이리스트 상세 조회")
    @GetMapping("/{playlistId}")
    fun getPlaylist(@PathVariable playlistId: Long): ResponseEntity<BaseResponse<PlaylistResponse>> {
        return BaseResponse(playlistService.getPlaylist(playlistId), 200).toEntity()
    }

    @Operation(summary = "플레이리스트 생성")
    @PostMapping
    @PreAuthorize("isAuthenticated()")
    fun createPlaylist(@RequestBody request: PlaylistCreateRequest): ResponseEntity<BaseResponse<PlaylistResponse>> {
        return BaseResponse(playlistService.createPlaylist(request), 201).toEntity()
    }

    @Operation(summary = "플레이리스트 수정")
    @PatchMapping("/{playlistId}")
    @PreAuthorize("isAuthenticated()")
    fun updatePlaylist(
        @PathVariable playlistId: Long,
        @RequestBody request: PlaylistUpdateRequest
    ): ResponseEntity<BaseResponse<PlaylistResponse>> {
        return BaseResponse(playlistService.updatePlaylist(playlistId, request), 200).toEntity()
    }

    @Operation(summary = "플레이리스트 삭제")
    @DeleteMapping("/{playlistId}")
    @PreAuthorize("isAuthenticated()")
    fun deletePlaylist(@PathVariable playlistId: Long): ResponseEntity<BaseResponse<PlaylistResponse>> {
        return BaseResponse(playlistService.deletePlaylist(playlistId), 200).toEntity()
    }
}
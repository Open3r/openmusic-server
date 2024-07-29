package com.open3r.openmusic.domain.playlist.controller

import com.open3r.openmusic.domain.playlist.dto.request.PlaylistAddSongRequest
import com.open3r.openmusic.domain.playlist.dto.request.PlaylistCreateRequest
import com.open3r.openmusic.domain.playlist.dto.request.PlaylistUpdateRequest
import com.open3r.openmusic.domain.playlist.service.PlaylistService
import com.open3r.openmusic.global.common.dto.response.BaseResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@Tag(name = "플레이리스트", description = "Playlist")
@RestController
@RequestMapping("/playlists")
class PlaylistController(
    private val playlistService: PlaylistService
) {
    @Operation(summary = "플레이리스트 목록 조회 (공개)")
    @GetMapping
    fun getPlaylists(@PageableDefault pageable: Pageable) =
        BaseResponse(playlistService.getPlaylists(pageable), 200).toEntity()

    @Operation(summary = "플레이리스트 조회")
    @GetMapping("/{playlistId}")
    fun getPlaylist(@PathVariable playlistId: Long) =
        BaseResponse(playlistService.getPlaylist(playlistId), 200).toEntity()

    @Operation(summary = "플레이리스트 검색")
    @GetMapping("/search")
    fun searchPlaylist(@RequestParam query: String, @PageableDefault pageable: Pageable) =
        BaseResponse(playlistService.searchPlaylists(query, pageable), 200).toEntity()

    @Operation(summary = "플레이리스트 생성")
    @PostMapping
    @PreAuthorize("isAuthenticated()")
    fun createPlaylist(@RequestBody @Valid request: PlaylistCreateRequest) =
        BaseResponse(playlistService.createPlaylist(request), 201).toEntity()

    @Operation(summary = "플레이리스트 수정")
    @PatchMapping("/{playlistId}")
    @PreAuthorize("isAuthenticated()")
    fun updatePlaylist(@PathVariable playlistId: Long, @RequestBody request: PlaylistUpdateRequest) =
        BaseResponse(playlistService.updatePlaylist(playlistId, request), 200).toEntity()

    @Operation(summary = "플레이리스트 삭제")
    @DeleteMapping("/{playlistId}")
    @PreAuthorize("isAuthenticated()")
    fun deletePlaylist(@PathVariable playlistId: Long) =
        BaseResponse(playlistService.deletePlaylist(playlistId), 204).toEntity()

    @Operation(summary = "플레이리스트 음악 추가")
    @PostMapping("/{playlistId}/songs")
    @PreAuthorize("isAuthenticated()")
    fun createPlaylistSong(@PathVariable playlistId: Long, @RequestBody request: PlaylistAddSongRequest) =
        BaseResponse(playlistService.addSongToPlaylist(playlistId, request.songId), 201).toEntity()

    @Operation(summary = "플레이리스트 음악 삭제")
    @DeleteMapping("/{playlistId}/songs/{songId}")
    @PreAuthorize("isAuthenticated()")
    fun deletePlaylistSong(@PathVariable playlistId: Long, @PathVariable songId: Long) =
        BaseResponse(playlistService.removeSongFromPlaylist(playlistId, songId), 200).toEntity()

    @Operation(summary = "플레이리스트 좋아요 추가")
    @PostMapping("/{playlistId}/likes")
    @PreAuthorize("isAuthenticated()")
    fun createPlaylistLike(@PathVariable playlistId: Long) =
        BaseResponse(playlistService.addLikeToPlaylist(playlistId), 201).toEntity()

    @Operation(summary = "플레이리스트 좋아요 삭제")
    @DeleteMapping("/{playlistId}/likes")
    @PreAuthorize("isAuthenticated()")
    fun deletePlaylistLike(@PathVariable playlistId: Long) =
        BaseResponse(playlistService.removeLikeFromPlaylist(playlistId), 200).toEntity()
}
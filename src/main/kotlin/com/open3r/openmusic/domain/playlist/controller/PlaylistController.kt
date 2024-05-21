package com.open3r.openmusic.domain.playlist.controller

import com.open3r.openmusic.domain.playlist.dto.request.PlaylistCreateRequest
import com.open3r.openmusic.domain.playlist.dto.request.PlaylistSongCreateRequest
import com.open3r.openmusic.domain.playlist.dto.request.PlaylistSongDeleteRequest
import com.open3r.openmusic.domain.playlist.service.PlaylistService
import com.open3r.openmusic.global.common.BaseResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*

@Tag(name = "플레이리스트", description = "Playlist")
@RestController
@RequestMapping("/playlists")
class PlaylistController(
    private val playlistService: PlaylistService
) {
    @Operation(summary = "플레이리스트 목록 조회")
    @GetMapping
    fun getPlaylists() = BaseResponse(playlistService.getPlaylists(), 200).toEntity()

    @Operation(summary = "플레이리스트 조회")
    @GetMapping("/{playlistId}")
    fun getPlaylist(@PathVariable playlistId: Long) =
        BaseResponse(playlistService.getPlaylist(playlistId), 200).toEntity()

    @Operation(summary = "플레이리스트 생성")
    @PostMapping
    fun createPlaylist(@RequestBody request: PlaylistCreateRequest) =
        BaseResponse(playlistService.createPlaylist(request), 201).toEntity()

    @Operation(summary = "플레이리스트 삭제")
    @DeleteMapping("/{playlistId}")
    fun deletePlaylist(@PathVariable playlistId: Long) =
        BaseResponse(playlistService.deletePlaylist(playlistId), 204).toEntity()

    @Operation(summary = "플레이리스트 음악 추가")
    @PostMapping("/{playlistId}/songs")
    fun createPlaylistSong(@PathVariable playlistId: Long, @RequestBody request: PlaylistSongCreateRequest) =
        BaseResponse(playlistService.createPlaylistSong(playlistId, request), 201).toEntity()

    @Operation(summary = "플레이리스트 음악 삭제")
    @DeleteMapping("/{playlistId}/songs")
    fun deletePlaylistSong(@PathVariable playlistId: Long, @RequestBody request: PlaylistSongDeleteRequest) =
        BaseResponse(playlistService.deletePlaylistSong(playlistId, request), 204).toEntity()

}
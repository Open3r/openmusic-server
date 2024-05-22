package com.open3r.openmusic.domain.album.controller

import com.open3r.openmusic.domain.album.dto.request.AlbumCreateRequest
import com.open3r.openmusic.domain.album.dto.request.AlbumUpdateRequest
import com.open3r.openmusic.domain.album.service.AlbumService
import com.open3r.openmusic.global.common.BaseResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@Tag(name = "앨범", description = "Album")
@RestController
@RequestMapping("/albums")
class AlbumController(
    private val albumService: AlbumService
) {
    @Operation(summary = "앨범 목록 조회")
    @GetMapping
    fun getAlbums() = BaseResponse(albumService.getAlbums(), 200).toEntity()

    @Operation(summary = "앨범 조회")
    @GetMapping("/{albumId}")
    fun getAlbum(@PathVariable albumId: Long) = BaseResponse(albumService.getAlbum(albumId), 200).toEntity()

    @Operation(summary = "앨범 검색")
    @GetMapping("/search")
    fun searchAlbum(@RequestParam query: String) = BaseResponse(albumService.searchAlbum(query), 200).toEntity()

    @Operation(summary = "앨범 생성")
    @PostMapping
    @PreAuthorize("isAuthenticated()")
    fun createAlbum(@RequestBody request: AlbumCreateRequest) =
        BaseResponse(albumService.createAlbum(request), 201).toEntity()

    @Operation(summary = "앨범 수정")
    @PatchMapping("/{albumId}")
    @PreAuthorize("isAuthenticated()")
    fun updateAlbum(@PathVariable albumId: Long, @RequestBody request: AlbumUpdateRequest) =
        BaseResponse(albumService.updateAlbum(albumId, request), 200).toEntity()

    @Operation(summary = "앨범 삭제")
    @DeleteMapping("/{albumId}")
    @PreAuthorize("isAuthenticated()")
    fun deleteAlbum(@PathVariable albumId: Long) = BaseResponse(albumService.deleteAlbum(albumId), 204).toEntity()

    @Operation(summary = "앨범 노래 추가")
    @PostMapping("/{albumId}/songs/{songId}")
    @PreAuthorize("isAuthenticated()")
    fun createAlbumSong(@PathVariable albumId: Long, @PathVariable songId: Long) =
        BaseResponse(albumService.createAlbumSong(albumId, songId), 201).toEntity()

    @Operation(summary = "앨범 노래 삭제")
    @DeleteMapping("/{albumId}/songs/{songId}")
    @PreAuthorize("isAuthenticated()")
    fun deleteAlbumSong(@PathVariable albumId: Long, @PathVariable songId: Long) =
        BaseResponse(albumService.deleteAlbumSong(albumId, songId), 204).toEntity()

    @Operation(summary = "앨범 좋아요 생성")
    @PostMapping("/{albumId}/likes")
    @PreAuthorize("isAuthenticated()")
    fun createAlbumLike(@PathVariable albumId: Long) =
        BaseResponse(albumService.createAlbumLike(albumId), 201).toEntity()

    @Operation(summary = "앨범 좋아요 삭제")
    @DeleteMapping("/{albumId}/likes")
    @PreAuthorize("isAuthenticated()")
    fun deleteAlbumLike(@PathVariable albumId: Long) =
        BaseResponse(albumService.deleteAlbumLike(albumId), 204).toEntity()
}
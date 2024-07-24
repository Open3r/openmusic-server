package com.open3r.openmusic.domain.album.controller

import com.open3r.openmusic.domain.album.dto.request.AlbumCreateRequest
import com.open3r.openmusic.domain.album.dto.request.AlbumUpdateRequest
import com.open3r.openmusic.domain.album.service.AlbumService
import com.open3r.openmusic.global.common.BaseResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
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
    @PreAuthorize("hasRole('ADMIN')")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "앨범 목록 조회 성공"),
            ApiResponse(responseCode = "401", description = "권한 없음"),
            ApiResponse(responseCode = "403", description = "접근 금지"),
            ApiResponse(responseCode = "404", description = "앨범 없음")
        ]
    )
    fun getAlbums() = BaseResponse(albumService.getAlbums(), 200).toEntity()

    @Operation(summary = "앨범 목록 조회 (공개)")
    @GetMapping("/public")
    fun getPublicAlbums() = BaseResponse(albumService.getPublicAlbums(), 200).toEntity()

    @Operation(summary = "앨범 목록 조회 (비공개)")
    @GetMapping("/private")
    @PreAuthorize("hasRole('ADMIN')")
    fun getPrivateAlbums() = BaseResponse(albumService.getPrivateAlbums(), 200).toEntity()

    @Operation(summary = "앨범 목록 조회 (내 앨범)")
    @GetMapping("/my")
    @PreAuthorize("isAuthenticated()")
    fun getMyAlbums() = BaseResponse(albumService.getMyAlbums(), 200).toEntity()

    @Operation(summary = "앨범 조회")
    @GetMapping("/{albumId}")
    fun getAlbum(@PathVariable albumId: Long) = BaseResponse(albumService.getAlbum(albumId), 200).toEntity()

    @Operation(summary = "앨범 검색")
    @GetMapping("/search")
    fun searchAlbum(@RequestParam query: String) = BaseResponse(albumService.searchAlbum(query), 200).toEntity()

    @Operation(summary = "앨범 생성")
    @PostMapping
    @PreAuthorize("isAuthenticated()")
    fun createAlbum(@RequestBody @Valid request: AlbumCreateRequest) =
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

    @Operation(summary = "앨범 좋아요 추가")
    @PostMapping("/{albumId}/likes")
    @PreAuthorize("isAuthenticated()")
    fun addAlbumLike(@PathVariable albumId: Long) =
        BaseResponse(albumService.addAlbumLike(albumId), 201).toEntity()

    @Operation(summary = "앨범 좋아요 삭제")
    @DeleteMapping("/{albumId}/likes")
    @PreAuthorize("isAuthenticated()")
    fun removeAlbumLike(@PathVariable albumId: Long) =
        BaseResponse(albumService.removeAlbumLike(albumId), 204).toEntity()
}
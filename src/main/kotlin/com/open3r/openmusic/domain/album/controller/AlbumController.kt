package com.open3r.openmusic.domain.album.controller

import com.open3r.openmusic.domain.album.dto.request.AlbumCreateRequest
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

    @Operation(summary = "앨범 삭제")
    @DeleteMapping("/{albumId}")
    @PreAuthorize("isAuthenticated()")
    fun deleteAlbum(@PathVariable albumId: Long) = BaseResponse(albumService.deleteAlbum(albumId), 204).toEntity()
}
package com.open3r.openmusic.domain.song.controller

import com.open3r.openmusic.domain.song.dto.request.SongCreateRequest
import com.open3r.openmusic.domain.song.service.SongService
import com.open3r.openmusic.global.common.BaseResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.MediaType
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@Tag(name = "음악", description = "Song")
@RestController
@RequestMapping("/songs")
class SongController(
    private val songService: SongService
) {
    @Operation(summary = "음악 목록 조회")
    @GetMapping
    fun getSongs() = BaseResponse(songService.getSongs(), 200).toEntity()

    @Operation(summary = "음악 조회")
    @GetMapping("/{songId}")
    fun getSong(@PathVariable songId: Long) = BaseResponse(songService.getSong(songId), 200).toEntity()

    @Operation(summary = "음악 검색")
    @GetMapping("/search")
    fun searchSong(@RequestParam query: String) = BaseResponse(songService.searchSong(query), 200).toEntity()

    @Operation(summary = "음악 생성")
    @PostMapping
    @PreAuthorize("isAuthenticated()")
    fun createSong(@RequestBody request: SongCreateRequest) =
        BaseResponse(songService.createSong(request), 201).toEntity()

    @Operation(summary = "음악 삭제")
    @DeleteMapping("/{songId}")
    @PreAuthorize("isAuthenticated()")
    fun deleteSong(@PathVariable songId: Long) = BaseResponse(songService.deleteSong(songId), 204).toEntity()

    @Operation(summary = "음악 업로드")
    @PostMapping("/upload", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    @PreAuthorize("isAuthenticated()")
    fun uploadSong(@RequestPart file: MultipartFile) = BaseResponse(songService.uploadSong(file), 201).toEntity()
}
package com.open3r.openmusic.domain.song.controller

import com.open3r.openmusic.domain.song.dto.request.SongCreateRequest
import com.open3r.openmusic.domain.song.dto.response.SongResponse
import com.open3r.openmusic.domain.song.dto.response.SongUploadResponse
import com.open3r.openmusic.domain.song.service.SongService
import com.open3r.openmusic.global.common.BaseResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@Tag(name = "음악", description = "Song")
@RestController
@RequestMapping("/songs")
class SongController(
    private val songService: SongService
) {
    @GetMapping
    fun getSongs(): ResponseEntity<BaseResponse<List<SongResponse>>> {
        return BaseResponse(songService.getSongs(), 200).toEntity()
    }

    @GetMapping("/{songId}")
    fun getSong(@PathVariable songId: Long): ResponseEntity<BaseResponse<SongResponse>> {
        return BaseResponse(songService.getSong(songId), 200).toEntity()
    }

    @GetMapping("/search")
    fun searchSong(@RequestParam query: String): ResponseEntity<BaseResponse<List<SongResponse>>> {
        return BaseResponse(songService.searchSong(query), 200).toEntity()
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    fun createSong(@ModelAttribute request: SongCreateRequest): ResponseEntity<BaseResponse<SongResponse>> {
        return BaseResponse(songService.createSong(request), 201).toEntity()
    }

    @DeleteMapping("/{songId}")
    fun deleteSong(@PathVariable songId: Long): ResponseEntity<BaseResponse<Unit>> {
        return BaseResponse(songService.deleteSong(songId), 200).toEntity()
    }

    @PostMapping("/upload", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun uploadSong(@RequestPart file: MultipartFile): ResponseEntity<BaseResponse<SongUploadResponse>> {
        return BaseResponse(songService.uploadSong(file), 201).toEntity()
    }
}
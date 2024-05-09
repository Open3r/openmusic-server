package com.open3r.openmusic.domain.song.controller

import com.open3r.openmusic.domain.song.dto.request.SongCreateRequest
import com.open3r.openmusic.domain.song.dto.response.SongResponse
import com.open3r.openmusic.domain.song.service.SongService
import com.open3r.openmusic.global.common.BaseResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@Tag(name = "노래", description = "Song")
@RestController
@RequestMapping("/songs")
class SongController(
    private val songService: SongService
) {
    @Operation(summary = "노래 목록 조회")
    @GetMapping
    fun getSongs(): ResponseEntity<BaseResponse<List<SongResponse>>> {
        return BaseResponse(songService.getSongs(), 200).toEntity()
    }

    @Operation(summary = "노래 상세 조회")
    @GetMapping("/{songId}")
    fun getSong(@PathVariable songId: Long): ResponseEntity<BaseResponse<SongResponse>> {
        return BaseResponse(songService.getSong(songId), 200).toEntity()
    }

    @Operation(summary = "노래 생성")
    @PostMapping
    fun createSong(@RequestBody request: SongCreateRequest): ResponseEntity<BaseResponse<SongResponse>> {
        return BaseResponse(songService.createSong(request), 201).toEntity()
    }

    @Operation(summary = "노래 삭제")
    @DeleteMapping("/{songId}")
    fun deleteSong(@PathVariable songId: Long): ResponseEntity<BaseResponse<SongResponse>> {
        return BaseResponse(songService.deleteSong(songId), 200).toEntity()
    }
}
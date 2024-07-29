package com.open3r.openmusic.domain.song.controller

import com.open3r.openmusic.domain.song.domain.enums.SongGenre
import com.open3r.openmusic.domain.song.dto.request.SongUpdateRequest
import com.open3r.openmusic.domain.song.service.SongService
import com.open3r.openmusic.global.common.dto.response.BaseResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@Tag(name = "음악", description = "Song")
@RestController
@RequestMapping("/songs")
class SongController(
    private val songService: SongService
) {
    @Operation(summary = "음악 목록 조회 (공개)")
    @GetMapping
    fun getSongs(@PageableDefault(sort = ["createdAt"]) pageable: Pageable) =
        BaseResponse(songService.getSongs(pageable), 200).toEntity()

    @Operation(summary = "음악 목록 조회 (장르 별)")
    @GetMapping("/genre")
    fun getGenreSongs(@RequestParam genre: SongGenre, @PageableDefault pageable: Pageable) =
        BaseResponse(songService.getGenreSongs(genre, pageable), 200).toEntity()

    @Operation(summary = "음악 목록 조회 (랭킹, 최대 100노래)")
    @GetMapping("/ranking")
    fun getRankingSongs(@PageableDefault pageable: Pageable) =
        BaseResponse(songService.getRankingSongs(pageable), 200).toEntity()

    @Operation(summary = "음악 조회")
    @GetMapping("/{songId}")
    fun getSong(@PathVariable songId: Long) = BaseResponse(songService.getSong(songId), 200).toEntity()

    @Operation(summary = "음악 검색")
    @GetMapping("/search")
    fun searchSongs(@RequestParam query: String, @PageableDefault pageable: Pageable) =
        BaseResponse(songService.searchSongs(query, pageable), 200).toEntity()

    @Operation(summary = "음악 수정")
    @PatchMapping("/{songId}")
    @PreAuthorize("isAuthenticated()")
    fun updateSong(@PathVariable songId: Long, @RequestBody request: SongUpdateRequest) =
        BaseResponse(songService.updateSong(songId, request), 200).toEntity()

    @Operation(summary = "음악 삭제")
    @DeleteMapping("/{songId}")
    @PreAuthorize("isAuthenticated()")
    fun deleteSong(@PathVariable songId: Long) = BaseResponse(songService.deleteSong(songId), 204).toEntity()

    @Operation(summary = "음악 좋아요 추가")
    @PostMapping("/{songId}/likes")
    @PreAuthorize("isAuthenticated()")
    fun addLikeToSong(@PathVariable songId: Long) =
        BaseResponse(songService.addLikeToSong(songId), 201).toEntity()

    @Operation(summary = "음악 좋아요 삭제")
    @DeleteMapping("/{songId}/likes")
    @PreAuthorize("isAuthenticated()")
    fun removeLikeToSong(@PathVariable songId: Long) =
        BaseResponse(songService.removeLikeFromSong(songId), 200).toEntity()
}
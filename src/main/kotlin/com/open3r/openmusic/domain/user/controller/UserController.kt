package com.open3r.openmusic.domain.user.controller

import com.open3r.openmusic.domain.user.dto.request.*
import com.open3r.openmusic.domain.user.service.UserService
import com.open3r.openmusic.global.common.dto.response.BaseResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@Tag(name = "유저", description = "User")
@RestController
@RequestMapping("/users")
class UserController(
    private val userService: UserService
) {
    @Operation(summary = "나 조회")
    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    fun getMe() = BaseResponse(userService.getMe(), 200).toEntity()

    @Operation(summary = "나 수정")
    @PatchMapping("/me")
    @PreAuthorize("isAuthenticated()")
    fun updateMe(@RequestBody @Valid request: UserUpdateRequest) = BaseResponse(userService.updateMe(request), 200).toEntity()

    @Operation(summary = "현재 재생 중인 노래 조회")
    @GetMapping("/me/now-playing")
    @PreAuthorize("isAuthenticated()")
    fun getMyNowPlaying() = BaseResponse(userService.getMyNowPlaying(), 200).toEntity()

    @Operation(summary = "현재 재생 중인 노래 설정")
    @PostMapping("/me/now-playing")
    @PreAuthorize("isAuthenticated()")
    fun setMyNowPlaying(@RequestBody request: UserSetNowPlayingRequest) =
        BaseResponse(userService.setMyNowPlaying(request), 201).toEntity()

    @Operation(summary = "내 앨범 조회")
    @GetMapping("/me/albums")
    @PreAuthorize("isAuthenticated()")
    fun getMyAlbums(@PageableDefault pageable: Pageable) =
        BaseResponse(userService.getMyAlbums(pageable), 200).toEntity()

    @Operation(summary = "내 노래 조회")
    @GetMapping("/me/songs")
    @PreAuthorize("isAuthenticated()")
    fun getMySongs(@PageableDefault pageable: Pageable) = BaseResponse(userService.getMySongs(pageable), 200).toEntity()

    @Operation(summary = "내 플레이리스트 조회")
    @GetMapping("/me/playlists")
    @PreAuthorize("isAuthenticated()")
    fun getMyPlaylists(@PageableDefault pageable: Pageable) =
        BaseResponse(userService.getMyPlaylists(pageable), 200).toEntity()

    @Operation(summary = "내 추천 노래 조회")
    @GetMapping("/me/recommendations")
    @PreAuthorize("isAuthenticated()")
    fun getMyRecommendations(@PageableDefault pageable: Pageable) =
        BaseResponse(userService.getMyRecommendations(pageable), 200).toEntity()

    @Operation(summary = "유저 장르 추가")
    @PostMapping("/me/genres")
    @PreAuthorize("isAuthenticated()")
    fun addGenre(@RequestBody request: UserAddGenreRequest) =
        BaseResponse(userService.addGenre(request), 201).toEntity()

    @Operation(summary = "유저 장르 삭제")
    @DeleteMapping("/me/genres")
    @PreAuthorize("isAuthenticated()")
    fun removeGenre(@RequestBody request: UserRemoveGenreRequest) =
        BaseResponse(userService.removeGenre(request), 204).toEntity()

    @Operation(summary = "유저 큐 조회")
    @GetMapping("/me/queue")
    @PreAuthorize("isAuthenticated()")
    fun getMyQueue() = BaseResponse(userService.getMyQueue(), 200).toEntity()

    @Operation(summary = "플레이리스트로 큐 설정")
    @PostMapping("/me/queue/playlist")
    @PreAuthorize("isAuthenticated()")
    fun addPlaylistSongsToQueue(@RequestBody request: UserCopyQueueFromPlaylistRequest) =
        BaseResponse(userService.addPlaylistSongsToQueue(request.playlistId), 201).toEntity()

    @Operation(summary = "앨범으로 큐 설정")
    @PostMapping("/me/queue/album")
    @PreAuthorize("isAuthenticated()")
    fun addAlbumSongsToQueue(@RequestBody request: UserCopyQueueFromAlbumRequest) =
        BaseResponse(userService.addAlbumSongsToQueue(request.albumId), 201).toEntity()

    @Operation(summary = "랭킹으로 큐 설정")
    @PostMapping("/me/queue/ranking")
    @PreAuthorize("isAuthenticated()")
    fun addRankingSongsToQueue() =
        BaseResponse(userService.addRankingSongsToQueue(), 201).toEntity()

    @Operation(summary = "최신 노래로 큐 설정")
    @PostMapping("/me/queue/latest")
    @PreAuthorize("isAuthenticated()")
    fun addLatestSongsToQueue() = BaseResponse(userService.addLatestSongsToQueue(), 201).toEntity()

    @Operation(summary = "유저 큐 추가")
    @PostMapping("/me/queue")
    @PreAuthorize("isAuthenticated()")
    fun addSongToQueue(@RequestBody request: UserAddQueueRequest) =
        BaseResponse(userService.addSongToQueue(request.songId), 201).toEntity()

    @Operation(summary = "유저 큐 삭제")
    @DeleteMapping("/me/queue/{songId}")
    @PreAuthorize("isAuthenticated()")
    fun removeSongFromQueue(@PathVariable songId: Long) =
        BaseResponse(userService.removeSongFromQueue(songId), 204).toEntity()

    @Operation(summary = "유저 큐 전체 삭제")
    @DeleteMapping("/me/queue")
    @PreAuthorize("isAuthenticated()")
    fun clearQueue() = BaseResponse(userService.clearQueue(), 204).toEntity()

    @Operation(summary = "유저 큐 셔플")
    @PostMapping("/me/queue/shuffle")
    @PreAuthorize("isAuthenticated()")
    fun shuffleQueue() = BaseResponse(userService.shuffleQueue(), 200).toEntity()

    @Operation(summary = "유저 최근 들은 노래 조회")
    @GetMapping("/me/recents")
    @PreAuthorize("isAuthenticated()")
    fun getMyRecents() = BaseResponse(userService.getMyRecents(), 200).toEntity()

    @Operation(summary = "유저 최근 들은 노래 추가")
    @PostMapping("/me/recents")
    @PreAuthorize("isAuthenticated()")
    fun addSongToRecents(@RequestBody request: UserAddRecentRequest) =
        BaseResponse(userService.addSongToRecents(request.songId), 201).toEntity()

    @Operation(summary = "유저 조회")
    @GetMapping("/{userId}")
    fun getUser(@PathVariable userId: Long) = BaseResponse(userService.getUser(userId), 200).toEntity()

    @Operation(summary = "유저 삭제")
    @DeleteMapping("/{userId}")
    fun deleteUser(@PathVariable userId: Long) = BaseResponse(userService.deleteUser(userId), 204).toEntity()

    @Operation(summary = "유저 앨범 조회")
    @GetMapping("/{userId}/albums")
    fun getUserAlbums(@PathVariable userId: Long) = BaseResponse(userService.getUserAlbums(userId), 200).toEntity()

    @Operation(summary = "유저 노래 조회")
    @GetMapping("/{userId}/songs")
    fun getUserSongs(@PathVariable userId: Long) = BaseResponse(userService.getUserSongs(userId), 200).toEntity()

    @Operation(summary = "유저 플레이리스트 조회")
    @GetMapping("/{userId}/playlists")
    fun getUserPlaylists(@PathVariable userId: Long) =
        BaseResponse(userService.getUserPlaylists(userId), 200).toEntity()
}
package com.open3r.openmusic.domain.user.controller

import com.open3r.openmusic.domain.user.dto.request.UserUpdateRequest
import com.open3r.openmusic.domain.user.service.UserService
import com.open3r.openmusic.global.common.BaseResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*

@Tag(name = "유저", description = "User")
@RestController
@RequestMapping("/users")
class UserController(
    private val userService: UserService
) {
    @Operation(summary = "유저 목록 조회")
    @GetMapping
    fun getUsers() = BaseResponse(userService.getUsers(), 200).toEntity()

    @Operation(summary = "나 수정")
    @PatchMapping("/me")
    fun updateMe(@RequestBody request: UserUpdateRequest) = BaseResponse(userService.updateMe(request), 200).toEntity()

    @Operation(summary = "유저 조회")
    @GetMapping("/{userId}")
    fun getUser(@PathVariable userId: Long) = BaseResponse(userService.getUser(userId), 200).toEntity()

    @Operation(summary = "유저 삭제")
    @DeleteMapping("/{userId}")
    fun deleteUser(@PathVariable userId: Long) = BaseResponse(userService.deleteUser(userId), 204).toEntity()
}
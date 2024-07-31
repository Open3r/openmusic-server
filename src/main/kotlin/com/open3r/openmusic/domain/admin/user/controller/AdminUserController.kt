package com.open3r.openmusic.domain.admin.user.controller

import com.open3r.openmusic.domain.admin.user.service.AdminUserService
import com.open3r.openmusic.global.common.dto.response.BaseResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@Tag(name = "관리자: 유저", description = "Admin: User")
@RestController
@RequestMapping("/admin/users")
class AdminUserController(
    private val adminUserService: AdminUserService
) {
    @Operation(summary = "유저 목록")
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    fun getUsers() = BaseResponse(adminUserService.getUsers(), 200).toEntity()

    @Operation(summary = "유저 삭제")
    @DeleteMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    fun deleteUser(@PathVariable userId: Long) = BaseResponse(adminUserService.deleteUser(userId), 204).toEntity()
}
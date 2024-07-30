package com.open3r.openmusic.domain.admin.user.controller

import com.open3r.openmusic.domain.admin.user.service.AdminUserService
import com.open3r.openmusic.global.common.dto.response.BaseResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "유저 관리자", description = "Admin User")
@RestController
@RequestMapping("/admin/users")
class AdminUserController(
    private val adminUserService: AdminUserService
) {
    @DeleteMapping("/{userId}")
    fun deleteUser(@PathVariable userId: Long) = BaseResponse(adminUserService.deleteUser(userId), 204).toEntity()
}
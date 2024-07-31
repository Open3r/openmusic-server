package com.open3r.openmusic.domain.admin.database.controller

import com.open3r.openmusic.domain.admin.database.service.AdminDatabaseService
import com.open3r.openmusic.global.common.dto.response.BaseResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "관리자: 데이터베이스", description = "Admin: Database")
@RestController
@RequestMapping("/admin/database")
class AdminDatabaseController(
    private val adminDatabaseService: AdminDatabaseService
) {
    @Operation(summary = "데이터베이스 초기화")
    @DeleteMapping("/clear")
    @PreAuthorize("hasRole('ADMIN')")
    fun clearDatabase() = BaseResponse(adminDatabaseService.clearDatabase(), 200).toEntity()
}
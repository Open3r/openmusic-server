package com.open3r.openmusic.domain.admin.report.controller

import com.open3r.openmusic.domain.admin.report.service.AdminReportService
import com.open3r.openmusic.global.common.dto.response.BaseResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@Tag(name = "어드민: 신고", description = "Admin: Report")
@RestController
@RequestMapping("/admin/reports")
class AdminReportController(
    private val adminReportService: AdminReportService
) {
    @Operation(summary = "대기 중인 신고 목록 조회")
    @GetMapping("/pending")
    @PreAuthorize("hasRole('ADMIN')")
    fun getPendingReports() = BaseResponse(adminReportService.getPendingReports(), 200).toEntity()

    @Operation(summary = "승인된 신고 목록 조회")
    @GetMapping("/approved")
    @PreAuthorize("hasRole('ADMIN')")
    fun getApprovedReports() = BaseResponse(adminReportService.getApprovedReports(), 200).toEntity()

    @Operation(summary = "거부된 신고 목록 조회")
    @GetMapping("/rejected")
    @PreAuthorize("hasRole('ADMIN')")
    fun getRejectedReports() = BaseResponse(adminReportService.getRejectedReports(), 200).toEntity()

    @Operation(summary = "신고 승인")
    @PostMapping("/{reportId}/approve")
    @PreAuthorize("hasRole('ADMIN')")
    fun approveReport(@PathVariable reportId: Long) =
        BaseResponse(adminReportService.approveReport(reportId), 200).toEntity()

    @Operation(summary = "신고 거부")
    @PostMapping("/{reportId}/reject")
    @PreAuthorize("hasRole('ADMIN')")
    fun rejectReport(@PathVariable reportId: Long) =
        BaseResponse(adminReportService.rejectReport(reportId), 200).toEntity()
}
package com.open3r.openmusic.domain.report.controller

import com.open3r.openmusic.domain.report.dto.request.ReportCreateRequest
import com.open3r.openmusic.domain.report.service.ReportService
import com.open3r.openmusic.global.common.dto.response.BaseResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@Tag(name = "신고", description = "Report")
@RestController
@RequestMapping("/reports")
class ReportController(
    private val reportService: ReportService
) {
    @Operation(summary = "내 신고 목록 조회")
    @GetMapping("/my")
    @PreAuthorize("isAuthenticated()")
    fun getMyReports() = BaseResponse(reportService.getMyReports(), 200).toEntity()

    @Operation(summary = "신고 생성")
    @PostMapping
    @PreAuthorize("isAuthenticated()")
    fun createReport(@RequestBody request: ReportCreateRequest) =
        BaseResponse(reportService.createReport(request), 201).toEntity()
}
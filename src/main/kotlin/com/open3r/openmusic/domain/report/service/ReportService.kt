package com.open3r.openmusic.domain.report.service

import com.open3r.openmusic.domain.report.dto.request.ReportCreateRequest
import com.open3r.openmusic.domain.report.dto.response.ReportResponse

interface ReportService {
    fun getMyReports(): List<ReportResponse>
    fun createReport(request: ReportCreateRequest)
}
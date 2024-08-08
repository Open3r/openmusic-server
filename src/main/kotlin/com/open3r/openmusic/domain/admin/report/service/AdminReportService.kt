package com.open3r.openmusic.domain.admin.report.service

import com.open3r.openmusic.domain.report.dto.response.ReportResponse

interface AdminReportService {
    fun getPendingReports(): List<ReportResponse>
    fun getApprovedReports(): List<ReportResponse>
    fun getRejectedReports(): List<ReportResponse>

    fun approveReport(reportId: Long)
    fun rejectReport(reportId: Long)
}
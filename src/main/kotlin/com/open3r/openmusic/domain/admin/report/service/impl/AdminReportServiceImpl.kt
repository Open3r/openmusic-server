package com.open3r.openmusic.domain.admin.report.service.impl

import com.open3r.openmusic.domain.admin.report.service.AdminReportService
import com.open3r.openmusic.domain.report.domain.enums.ReportStatus
import com.open3r.openmusic.domain.report.dto.response.ReportResponse
import com.open3r.openmusic.domain.report.repository.ReportRepository
import com.open3r.openmusic.global.error.CustomException
import com.open3r.openmusic.global.error.ErrorCode
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AdminReportServiceImpl(
    private val reportRepository: ReportRepository
) : AdminReportService {
    @Transactional
    override fun getPendingReports(): List<ReportResponse> {
        val reports = reportRepository.findAllByStatus(ReportStatus.PENDING)

        return reports.map { ReportResponse.of(it) }
    }

    @Transactional
    override fun getApprovedReports(): List<ReportResponse> {
        val reports = reportRepository.findAllByStatus(ReportStatus.APPROVED)

        return reports.map { ReportResponse.of(it) }
    }

    @Transactional
    override fun getRejectedReports(): List<ReportResponse> {
        val reports = reportRepository.findAllByStatus(ReportStatus.REJECTED)

        return reports.map { ReportResponse.of(it) }
    }

    @Transactional
    override fun approveReport(reportId: Long) {
        val report = reportRepository.findByIdOrNull(reportId) ?: throw CustomException(ErrorCode.REPORT_NOT_FOUND)

        report.status = ReportStatus.APPROVED

        reportRepository.save(report)
    }

    @Transactional
    override fun rejectReport(reportId: Long) {
        val report = reportRepository.findByIdOrNull(reportId) ?: throw CustomException(ErrorCode.REPORT_NOT_FOUND)

        report.status = ReportStatus.REJECTED

        reportRepository.save(report)
    }
}
package com.open3r.openmusic.domain.report.service.impl

import com.open3r.openmusic.domain.report.domain.entity.ReportEntity
import com.open3r.openmusic.domain.report.domain.enums.ReportStatus
import com.open3r.openmusic.domain.report.dto.request.ReportCreateRequest
import com.open3r.openmusic.domain.report.dto.response.ReportResponse
import com.open3r.openmusic.domain.report.repository.ReportRepository
import com.open3r.openmusic.domain.report.service.ReportService
import com.open3r.openmusic.global.security.UserSecurity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ReportServiceImpl(
    private val userSecurity: UserSecurity,
    private val reportRepository: ReportRepository,

    ) : ReportService {
    @Transactional
    override fun getMyReports(): List<ReportResponse> {
        val reports = reportRepository.findAllByUser(userSecurity.user)

        return reports.map { ReportResponse.of(it) }
    }

    @Transactional
    override fun createReport(request: ReportCreateRequest) {
        val report = ReportEntity(
            title = request.title,
            description = request.description,
            user = userSecurity.user,
            status = ReportStatus.PENDING
        )

        reportRepository.save(report)
    }
}
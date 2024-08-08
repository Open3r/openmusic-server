package com.open3r.openmusic.domain.report.dto.response

import com.open3r.openmusic.domain.report.domain.entity.ReportEntity
import com.open3r.openmusic.domain.report.domain.enums.ReportStatus
import com.open3r.openmusic.domain.user.dto.response.UserResponse
import java.time.LocalDateTime

data class ReportResponse(
    val id: Long,
    val title: String,
    val description: String,
    val status: ReportStatus,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val user: UserResponse,
) {
    companion object {
        fun of(report: ReportEntity) = ReportResponse(
            id = report.id!!,
            title = report.title,
            description = report.description,
            status = report.status,
            createdAt = report.createdAt!!,
            updatedAt = report.updatedAt!!,
            user = UserResponse.of(report.user),
        )
    }
}
package com.open3r.openmusic.domain.report.repository

import com.open3r.openmusic.domain.report.domain.entity.ReportEntity
import com.open3r.openmusic.domain.report.domain.enums.ReportStatus
import com.open3r.openmusic.domain.user.domain.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ReportRepository : JpaRepository<ReportEntity, Long> {
    fun findAllByUser(user: UserEntity): List<ReportEntity>
    fun findAllByStatus(status: ReportStatus): List<ReportEntity>
}
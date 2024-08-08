package com.open3r.openmusic.domain.report.domain.entity

import com.open3r.openmusic.domain.report.domain.enums.ReportStatus
import com.open3r.openmusic.domain.user.domain.entity.UserEntity
import com.open3r.openmusic.global.common.domain.entity.BaseEntity
import jakarta.persistence.*

@Entity
@Table(name = "reports")
class ReportEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "title", nullable = true)
    var title: String,

    @Column(name = "description", nullable = true, columnDefinition = "LONGTEXT")
    var description: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = true)
    var status: ReportStatus,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: UserEntity,
) : BaseEntity()
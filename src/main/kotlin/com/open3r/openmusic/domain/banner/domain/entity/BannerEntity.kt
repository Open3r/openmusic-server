package com.open3r.openmusic.domain.banner.domain.entity

import com.open3r.openmusic.global.common.domain.entity.BaseEntity
import jakarta.persistence.*

@Entity
@Table(name = "banners")
class BannerEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "url", nullable = false, updatable = false)
    val url: String,

    @Column(name = "image_url", nullable = false, updatable = false)
    val imageUrl: String,
) : BaseEntity()
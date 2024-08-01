package com.open3r.openmusic.domain.banner.repository

import com.open3r.openmusic.domain.banner.domain.entity.BannerEntity
import org.springframework.data.jpa.repository.JpaRepository

interface BannerRepository : JpaRepository<BannerEntity, Long>
package com.open3r.openmusic.global.common.dto.request

data class PageRequest(
    val page: Int,
    val size: Int,
    val sort: String,
    val order: String,
)
package com.open3r.openmusic.global.common

import org.springframework.http.ResponseEntity

class BaseResponse<T>(
    val data: T,
    val status: Int,
) {
    fun toEntity() = ResponseEntity
        .status(status)
        .body(this)
}
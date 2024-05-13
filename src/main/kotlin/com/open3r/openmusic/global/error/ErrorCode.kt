package com.open3r.openmusic.global.error

import org.springframework.http.HttpStatus

enum class ErrorCode(
    val status: HttpStatus,
    val message: String
)
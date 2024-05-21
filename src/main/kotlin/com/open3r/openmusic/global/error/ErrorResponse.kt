package com.open3r.openmusic.global.error

import org.springframework.http.ResponseEntity

data class ErrorResponse(
    private val error: ErrorCode,
) {
    val status = error.status.value()
    val code = error.name
    val message = error.message

    fun toEntity() = ResponseEntity.status(status).body(this)
}
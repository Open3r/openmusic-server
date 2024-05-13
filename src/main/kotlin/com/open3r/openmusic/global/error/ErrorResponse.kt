package com.open3r.openmusic.global.error

import org.springframework.http.ResponseEntity

data class ErrorResponse(
    val status: Int,
    val code: String,
    val message: String
) {
    companion object {
        fun toEntity(code: ErrorCode) = ResponseEntity.status(code.status).body(
            ErrorResponse(
                code.status.value(),
                code.name,
                code.message
            )
        )
    }
}
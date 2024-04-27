package com.open3r.openmusic.global.exception

import org.springframework.http.ResponseEntity

data class ErrorResponse(
    val status: Int,
    val code: String,
    val message: String
) {
    companion object {
        fun toResponseEntity(errorCode: ErrorCode) = ResponseEntity.status(errorCode.status).body(
            ErrorResponse(
                errorCode.status.value(),
                errorCode.name,
                errorCode.message
            )
        )
    }
}
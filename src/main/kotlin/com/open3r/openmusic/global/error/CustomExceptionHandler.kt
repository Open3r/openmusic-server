package com.open3r.openmusic.global.error

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class CustomExceptionHandler {
    @ExceptionHandler(CustomException::class)
    fun handleCustomException(e: CustomException): ResponseEntity<ErrorResponse> {
        return ErrorResponse.toEntity(e.code)
    }
}
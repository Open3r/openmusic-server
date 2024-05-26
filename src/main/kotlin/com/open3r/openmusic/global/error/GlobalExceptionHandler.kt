package com.open3r.openmusic.global.error

import com.open3r.openmusic.global.properties.DiscordProperties
import com.open3r.openmusic.global.util.DiscordUtil
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.UnsupportedJwtException
import net.dv8tion.jda.api.JDA
import org.springframework.http.ResponseEntity
import org.springframework.security.access.AccessDeniedException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.multipart.MaxUploadSizeExceededException
import org.springframework.web.servlet.NoHandlerFoundException

@ControllerAdvice
class GlobalExceptionHandler(
    private val jda: JDA,
    private val discordProperties: DiscordProperties
) {
    @ExceptionHandler(CustomException::class)
    fun handleCustomException(e: CustomException): ResponseEntity<ErrorResponse> {
        return ErrorResponse(e.error).toEntity()
    }

    @ExceptionHandler(MaxUploadSizeExceededException::class)
    fun handleMaxUploadSizeExceededException(e: MaxUploadSizeExceededException): ResponseEntity<ErrorResponse> {
        return ErrorResponse(ErrorCode.MAX_UPLOAD_SIZE_EXCEEDED).toEntity()
    }

    @ExceptionHandler(NoHandlerFoundException::class)
    fun handleNoHandlerFoundException(e: NoHandlerFoundException): ResponseEntity<ErrorResponse> {
        return ErrorResponse(ErrorCode.NOT_FOUND).toEntity()
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(e: MethodArgumentNotValidException): ResponseEntity<ErrorResponse> {
        return ErrorResponse(ErrorCode.INVALID_INPUT_VALUE).toEntity()
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException::class)
    fun handleHttpRequestMethodNotSupportedException(e: HttpRequestMethodNotSupportedException): ResponseEntity<ErrorResponse> {
        return ErrorResponse(ErrorCode.METHOD_NOT_ALLOWED).toEntity()
    }

    @ExceptionHandler(AccessDeniedException::class)
    fun handleAccessDeniedException(e: AccessDeniedException): ResponseEntity<ErrorResponse> {
        return ErrorResponse(ErrorCode.FORBIDDEN).toEntity()
    }

    @ExceptionHandler(ExpiredJwtException::class)
    fun handleExpiredJwtException(e: ExpiredJwtException): ResponseEntity<ErrorResponse> {
        return ErrorResponse(ErrorCode.EXPIRED_ACCESS_TOKEN).toEntity()
    }

    @ExceptionHandler(UnsupportedJwtException::class)
    fun handleUnsupportedJwtException(e: UnsupportedJwtException): ResponseEntity<ErrorResponse> {
        return ErrorResponse(ErrorCode.UNSUPPORTED_ACCESS_TOKEN).toEntity()
    }

    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseEntity<ErrorResponse> {
        DiscordUtil.sendException(jda.getTextChannelById(discordProperties.channelId)!!, e)

        return ErrorResponse(ErrorCode.UNKNOWN).toEntity()
    }
}
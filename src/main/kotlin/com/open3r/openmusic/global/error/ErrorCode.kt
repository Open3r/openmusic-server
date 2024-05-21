package com.open3r.openmusic.global.error

import org.springframework.http.HttpStatus

enum class ErrorCode(
    val status: HttpStatus,
    val message: String
) {
    // Common
    UNKNOWN(HttpStatus.INTERNAL_SERVER_ERROR, "Unknown"),
    NOT_FOUND(HttpStatus.NOT_FOUND, "Not found"),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "Method not allowed"),
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "Invalid input value"),
    UNSUPPORTED_REGISTRATION_ID(HttpStatus.BAD_REQUEST, "Unsupported registration id"),

    // Jwt
    EXPIRED_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "Expired access token"),
    INVALID_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "Invalid access token"),
    UNSUPPORTED_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "Unsupported access token"),

    EXPIRED_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "Expired refresh token"),
    INVALID_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "Invalid refresh token"),

    // Auth
    INVALID_EMAIL(HttpStatus.BAD_REQUEST, "Invalid email"),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "Invalid password"),
    AUTHENTICATION_FAILED(HttpStatus.BAD_REQUEST, "Authentication failed"),

    // Song
    SONG_NOT_FOUND(HttpStatus.NOT_FOUND, "Song not found"),
    SONG_EMPTY(HttpStatus.BAD_REQUEST, "Song is empty"),

    // User
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "User not found"),
    USER_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "User already exists"),


    // Else
    MAX_UPLOAD_SIZE_EXCEEDED(HttpStatus.BAD_REQUEST, "Max upload size exceeded")
}
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
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "Unauthorized"),
    FORBIDDEN(HttpStatus.FORBIDDEN, "Forbidden"),

    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "Invalid input value"),
    UNSUPPORTED_REGISTRATION_ID(HttpStatus.BAD_REQUEST, "Unsupported registration id"),

    // Jwt
    EXPIRED_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "Expired access token"),
    INVALID_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "Invalid access token"),
    UNSUPPORTED_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "Unsupported access token"),

    EXPIRED_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "Expired refresh token"),
    INVALID_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "Invalid refresh token"),
    UNSUPPORTED_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "Unsupported refresh token"),

    // Auth
    CERTIFICATION_NUMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "Certification number not found"),
    CERTIFICATION_NUMBER_NOT_MATCH(HttpStatus.BAD_REQUEST, "Certification number not match"),


    // Album
    ALBUM_NOT_FOUND(HttpStatus.NOT_FOUND, "Album not found"),
    ALBUM_NOT_DELETABLE(HttpStatus.BAD_REQUEST, "Album is not deletable"),
    ALBUM_NOT_UPDATABLE(HttpStatus.BAD_REQUEST, "Album is not updatable"),
    ALBUM_SONG_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "Album song already exists"),
    ALBUM_SONG_NOT_FOUND(HttpStatus.NOT_FOUND, "Album song not found"),
    ALBUM_LIKE_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "Album like already exists"),
    ALBUM_LIKE_NOT_FOUND(HttpStatus.NOT_FOUND, "Album like not found"),

    // Mail
    MAIL_SEND_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Mail send error"),

    // Playlist
    PLAYLIST_NOT_FOUND(HttpStatus.NOT_FOUND, "Playlist not found"),
    PLAYLIST_NOT_DELETABLE(HttpStatus.BAD_REQUEST, "Playlist is not deletable"),
    PLAYLIST_NOT_UPDATABLE(HttpStatus.BAD_REQUEST, "Playlist is not updatable"),
    PLAYLIST_SONG_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "Playlist song already exists"),
    PLAYLIST_SONG_NOT_FOUND(HttpStatus.NOT_FOUND, "Playlist song not found"),
    PLAYLIST_LIKE_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "Playlist like already exists"),
    PLAYLIST_LIKE_NOT_FOUND(HttpStatus.NOT_FOUND, "Playlist like not found"),

    // Song
    SONG_NOT_FOUND(HttpStatus.NOT_FOUND, "Song not found"),
    SONG_NOT_DELETABLE(HttpStatus.BAD_REQUEST, "Song is not deletable"),
    SONG_LIKE_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "Song like already exists"),
    SONG_LIKE_NOT_FOUND(HttpStatus.NOT_FOUND, "Song like not found"),
    SONG_NOT_UPDATABLE(HttpStatus.BAD_REQUEST, "Song is not updatable"),

    // User
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "User not found"),
    USER_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "User already exists"),
    USER_NOT_DELETABLE(HttpStatus.BAD_REQUEST, "User is not deletable"),
    USER_ALREADY_VERIFIED(HttpStatus.BAD_REQUEST, "User already verified"),
    USER_PASSWORD_NOT_MATCH(HttpStatus.BAD_REQUEST, "User password not match"),
    USER_NOT_VERIFIED(HttpStatus.BAD_REQUEST, "User not verified"),

    // Else
    MAX_UPLOAD_SIZE_EXCEEDED(HttpStatus.BAD_REQUEST, "Max upload size exceeded")

}
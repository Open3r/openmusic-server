package com.open3r.openmusic.global.common

import com.fasterxml.jackson.annotation.JsonInclude
import org.springframework.http.ResponseEntity

@JsonInclude(JsonInclude.Include.NON_NULL)
class BaseResponse<T>(
    val data: T,
    val status: Int,
) {
    fun toEntity() = ResponseEntity
        .status(status)
        .body(this)
}
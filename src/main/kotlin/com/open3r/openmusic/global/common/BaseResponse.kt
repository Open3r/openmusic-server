package com.open3r.openmusic.global.common

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
class BaseResponse<T>(
    val data: T?,
    val status: Int?,
    val message: String?
)
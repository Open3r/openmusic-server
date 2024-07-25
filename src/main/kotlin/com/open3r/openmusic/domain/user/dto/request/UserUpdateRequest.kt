package com.open3r.openmusic.domain.user.dto.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import org.hibernate.validator.constraints.URL

data class UserUpdateRequest(
    val nickname: String?,
    @field:URL
    val avatarUrl: String?,
    @field:Size(min = 8)
    val password: String?,
    @field:NotBlank
    @field:Size(min = 8)
    val currentPassword: String,
)
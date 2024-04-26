package com.open3r.openmusic.domain.user.controller

import com.open3r.openmusic.domain.user.dto.response.UserResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class UserController {
    @GetMapping("/me")
    fun getMe(): UserResponse {
        return UserResponse(
            "Joe da bovo",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT5ay_I4fQMfRIknHYt_iva3_-Hmat46b0Fh1HvUOXlNw&s"
        )
    }
}
package com.open3r.openmusic.domain.email.controller

import com.open3r.openmusic.domain.email.dto.request.EmailSendRequest
import com.open3r.openmusic.domain.email.service.EmailService
import com.open3r.openmusic.global.common.dto.response.BaseResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*

@Tag(name = "이메일", description = "Email")
@RestController
@RequestMapping("/email")
class EmailController(
    private val emailService: EmailService
) {
    @Operation(summary = "이메일 전송")
    @PostMapping("/send")
    fun sendEmail(@RequestBody request: EmailSendRequest) =
        BaseResponse(emailService.sendEmail(request), 200).toEntity()

    @Operation(summary = "이메일 중복 확인")
    @GetMapping("/check")
    fun checkEmail(@RequestParam email: String) = BaseResponse(emailService.checkEmail(email), 200).toEntity()
}
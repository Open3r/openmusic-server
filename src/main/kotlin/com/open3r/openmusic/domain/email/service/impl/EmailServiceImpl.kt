package com.open3r.openmusic.domain.email.service.impl

import com.open3r.openmusic.domain.email.dto.request.EmailSendRequest
import com.open3r.openmusic.domain.email.dto.response.EmailCheckResponse
import com.open3r.openmusic.domain.email.dto.response.EmailSendResponse
import com.open3r.openmusic.domain.email.repository.EmailCodeRepository
import com.open3r.openmusic.domain.email.service.EmailService
import com.open3r.openmusic.domain.user.repository.UserRepository
import com.open3r.openmusic.global.error.CustomException
import com.open3r.openmusic.global.error.ErrorCode
import com.open3r.openmusic.logger
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.security.SecureRandom

@Service
class EmailServiceImpl(
    private val mailSender: JavaMailSender,
    private val userRepository: UserRepository,
    private val emailCodeRepository: EmailCodeRepository
) : EmailService {
    @Transactional
    override fun sendEmail(request: EmailSendRequest): EmailSendResponse {
        val email = request.email

        if (userRepository.existsByEmail(email)) throw CustomException(ErrorCode.USER_ALREADY_EXISTS)
        if (emailCodeRepository.existsByEmail(email)) {
            val code = emailCodeRepository.findByEmail(email) ?: throw CustomException(ErrorCode.EMAIL_CODE_NOT_FOUND)
            emailCodeRepository.save(email, code)

            logger().info("Email: $email, Code: $code")

            return EmailSendResponse(email = email, resend = false)
        }

        val code = String.format("%06d", SecureRandom.getInstanceStrong().nextInt(999999))

        emailCodeRepository.save(email, code)

        val message = mailSender.createMimeMessage()

        MimeMessageHelper(message, false, "UTF-8").apply {
            setTo(email)
            setSubject("OpenMusic 인증 번호")
            setText("인증 번호: $code")
        }

        mailSender.send(message)

        return EmailSendResponse(email = email, resend = true)
    }

    @Transactional(readOnly = true)
    override fun checkEmail(email: String): EmailCheckResponse {
        return EmailCheckResponse(exists = userRepository.existsByEmail(email))
    }
}
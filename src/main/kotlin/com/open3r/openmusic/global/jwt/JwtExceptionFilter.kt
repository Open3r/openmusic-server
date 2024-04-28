package com.open3r.openmusic.global.jwt

import com.fasterxml.jackson.databind.ObjectMapper
import com.open3r.openmusic.global.exception.CustomException
import com.open3r.openmusic.global.exception.ErrorCode
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.UnsupportedJwtException
import io.jsonwebtoken.security.SecurityException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.filter.OncePerRequestFilter


class JwtExceptionFilter : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            filterChain.doFilter(request, response)
        } catch (e: Exception) {
            when (e) {
                is ExpiredJwtException -> {
                    setErrorResponse(response, CustomException(ErrorCode.EXPIRED_ACCESS_TOKEN))
                }
                is UnsupportedJwtException -> {
                    setErrorResponse(response, CustomException(ErrorCode.UNSUPPORTED_ACCESS_TOKEN))
                }
                is SecurityException, is MalformedJwtException -> {
                    setErrorResponse(response, CustomException(ErrorCode.INVALID_ACCESS_TOKEN))
                }
                else -> {
                    setErrorResponse(response, e)
                }
            }
        }
    }

    private fun setErrorResponse(response: HttpServletResponse, e: Exception) {
        val mapper = ObjectMapper()
        val map: MutableMap<String, String?> = HashMap()

        response.contentType = "application/json;charset=UTF-8"
        response.status = 401

        map["status"] = "401"

        if (e is CustomException) {
            map["code"] = e.code.name
            map["message"] = e.code.message
        } else {
            map["code"] = "UNKNOWN"
            map["message"] = "알 수 없는 오류가 발생했습니다."
        }

        response.writer.write(mapper.writeValueAsString(map))
    }
}
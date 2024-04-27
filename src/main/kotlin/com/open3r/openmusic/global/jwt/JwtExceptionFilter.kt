package com.open3r.openmusic.global.jwt

import com.fasterxml.jackson.databind.ObjectMapper
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
            setErrorResponse(response, e)
        }
    }

    private fun setErrorResponse(response: HttpServletResponse, e: Exception) {
        val mapper = ObjectMapper()
        val map: MutableMap<String, String?> = HashMap()

        response.contentType = "application/json;charset=UTF-8"

        response.status = 401

        map["status"] = "401"
        map["message"] = e.message

        response.writer.write(mapper.writeValueAsString(map))
    }
}
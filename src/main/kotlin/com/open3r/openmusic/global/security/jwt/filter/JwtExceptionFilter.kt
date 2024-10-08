package com.open3r.openmusic.global.security.jwt.filter

import com.fasterxml.jackson.databind.ObjectMapper
import com.open3r.openmusic.global.config.discord.DiscordProperties
import com.open3r.openmusic.global.error.CustomException
import com.open3r.openmusic.global.error.ErrorCode
import com.open3r.openmusic.global.util.DiscordUtil
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.UnsupportedJwtException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import net.dv8tion.jda.api.JDA
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtExceptionFilter(
    private val jda: JDA,
    private val discordProperties: DiscordProperties
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            filterChain.doFilter(request, response)
        } catch (e: ExpiredJwtException) {
            setErrorResponse(response, CustomException(ErrorCode.EXPIRED_ACCESS_TOKEN))
        } catch (e: UnsupportedJwtException) {
            setErrorResponse(response, CustomException(ErrorCode.UNSUPPORTED_ACCESS_TOKEN))
        } catch (e: MalformedJwtException) {
            setErrorResponse(response, CustomException(ErrorCode.INVALID_ACCESS_TOKEN))
        } catch (e: SecurityException) {
            setErrorResponse(response, CustomException(ErrorCode.INVALID_ACCESS_TOKEN))
        } catch (e: IllegalArgumentException) {
            setErrorResponse(response, CustomException(ErrorCode.INVALID_ACCESS_TOKEN))
        } catch (e: Exception) {
            setErrorResponse(response, CustomException(ErrorCode.UNKNOWN))

            DiscordUtil.sendException(jda.getTextChannelById(discordProperties.channelId)!!, e)
        }
    }

    private fun setErrorResponse(response: HttpServletResponse, e: CustomException) {
        val mapper = ObjectMapper()
        val map = HashMap<String, Any?>()

        response.contentType = "application/json;charset=UTF-8"

        val error = e.error

        response.status = error.status.value()

        map["status"] = error.status.value()
        map["message"] = error.message

        response.writer.write(mapper.writeValueAsString(map))
    }
}
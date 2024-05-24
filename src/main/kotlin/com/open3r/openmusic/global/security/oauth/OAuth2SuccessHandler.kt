package com.open3r.openmusic.global.security.oauth

import com.open3r.openmusic.global.security.jwt.JwtProvider
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler
import org.springframework.stereotype.Component
import org.springframework.web.util.UriComponentsBuilder
import java.nio.charset.StandardCharsets

@Component
class OAuth2SuccessHandler(private val jwtProvider: JwtProvider) : SimpleUrlAuthenticationSuccessHandler() {
    override fun onAuthenticationSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication
    ) {
        val token = jwtProvider.generateToken(authentication)
        val url = UriComponentsBuilder.fromUriString(REDIRECT_URI)
            .queryParam("accessToken", token.accessToken)
            .queryParam("refreshToken", token.refreshToken)
            .build()
            .encode(StandardCharsets.UTF_8)
            .toUriString()
        redirectStrategy.sendRedirect(request, response, url)
    }

    companion object {
        const val REDIRECT_URI = "https://openmusic.com/"
    }
}
package com.open3r.openmusic.domain.auth.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.open3r.openmusic.global.properties.GoogleProperties
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.reactive.function.client.WebClient
import java.util.concurrent.ConcurrentHashMap

@Service
class GoogleServiceImpl(
    private val restTemplate: RestTemplate,
    private val googleProperties: GoogleProperties
) : GoogleService {
    override fun getAccessToken(code: String): String? {
        val url = "https://oauth2.googleapis.com/token"
        val params = ConcurrentHashMap<String, String>()

        params["code"] = code
        params["client_id"] = googleProperties.clientId
        params["client_secret"] = googleProperties.clientSecret
        params["redirect_uri"] = googleProperties.redirectUri
        params["grant_type"] = "authorization_code"

        val response = restTemplate.postForEntity(url, params, String::class.java)

        if (response.statusCode == HttpStatus.OK) {
            val body = response.body

            try {
                val mapper = ObjectMapper()
                val node = mapper.readTree(body)

                val accessToken = node.get("access_token").asText()

                return accessToken
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        return null
    }

    override fun getUserInfo(accessToken: String): String? {
        val client = WebClient.builder()
            .baseUrl("https://www.googleapis.com")
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build()

        val response = client.get()
            .uri {
                it.path("/oauth2/v2/userinfo")
                    .build()
            }
            .header("Authorization", "Bearer $accessToken")
            .retrieve()
            .bodyToMono(String::class.java)
            .block()

        return response
    }
}
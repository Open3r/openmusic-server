package com.open3r.openmusic.global.security.oauth

import com.open3r.openmusic.global.error.CustomException
import com.open3r.openmusic.global.error.ErrorCode

data class OAuth2UserInfo(
    val name: String,
    val email: String,
    val profile: String
) {
    companion object {
        fun of(registrationId: String, attributes: MutableMap<String, Any>): OAuth2UserInfo {
            return when (registrationId) {
                "google" -> ofGoogle(attributes)
                "kakao" -> ofKakao(attributes)
                else -> throw CustomException(ErrorCode.UNSUPPORTED_REGISTRATION_ID)
            }
        }

        private fun ofGoogle(attributes: Map<String, Any>): OAuth2UserInfo {
            return OAuth2UserInfo(
                name = attributes["name"] as String,
                email = attributes["email"] as String,
                profile = attributes["picture"] as String
            )
        }

        private fun ofKakao(attributes: Map<String, Any>): OAuth2UserInfo {
            val account = attributes["kakao_account"] as Map<*, *>
            val profile = account["profile"] as Map<*, *>

            return OAuth2UserInfo(
                name = profile["nickname"] as String,
                email = account["email"] as String,
                profile = profile["profile_image_url"] as String
            )
        }
    }
}
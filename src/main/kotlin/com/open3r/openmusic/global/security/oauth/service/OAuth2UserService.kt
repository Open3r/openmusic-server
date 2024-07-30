package com.open3r.openmusic.global.security.oauth.service

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.user.OAuth2User

class OAuth2UserService : DefaultOAuth2UserService() {
    override fun loadUser(userRequest: OAuth2UserRequest): OAuth2User {
        val oAuth2User = super.loadUser(userRequest)
        val attributes = oAuth2User.attributes

        val userNameAttributeName =
            userRequest.clientRegistration.providerDetails.userInfoEndpoint.userNameAttributeName

        TODO()
    }
}
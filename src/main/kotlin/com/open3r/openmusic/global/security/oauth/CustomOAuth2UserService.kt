package com.open3r.openmusic.global.security.oauth

import com.open3r.openmusic.domain.user.domain.User
import com.open3r.openmusic.domain.user.domain.UserProvider
import com.open3r.openmusic.domain.user.domain.UserRole
import com.open3r.openmusic.domain.user.repository.UserRepository
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service

@Service
class CustomOAuth2UserService(
    private val userRepository: UserRepository
) : DefaultOAuth2UserService() {
    override fun loadUser(userRequest: OAuth2UserRequest): OAuth2User {
        val attributes = super.loadUser(userRequest).attributes

        val registrationId = userRequest.clientRegistration.registrationId
        val userNameAttributeName =
            userRequest.clientRegistration.providerDetails.userInfoEndpoint.userNameAttributeName
        val info = OAuth2UserInfo.of(registrationId, attributes)
        val provider = UserProvider.valueOf(registrationId.uppercase())

        val user = userRepository.findByEmailAndProvider(info.email, provider) ?: userRepository.save(
            User(
                name = info.name,
                email = info.email,
                password = "",
                provider = provider,
                providerId = "${attributes[userNameAttributeName]}",
                profileUrl = info.profile,
                role = UserRole.USER
            )
        )

        return CustomOAuth2User(user, attributes)
    }
}
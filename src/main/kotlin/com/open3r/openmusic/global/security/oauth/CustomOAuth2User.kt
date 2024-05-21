package com.open3r.openmusic.global.security.oauth

import com.open3r.openmusic.domain.user.domain.User
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.oauth2.core.user.OAuth2User

class CustomOAuth2User(
    val user: User,
    private val attributes: MutableMap<String, Any>
) : OAuth2User, UserDetails {
    override fun getName() = user.providerId
    override fun getAttributes() = attributes
    override fun getPassword() = ""
    override fun getUsername() = user.email
    override fun isAccountNonExpired() = true
    override fun isAccountNonLocked() = true
    override fun isCredentialsNonExpired() = true
    override fun isEnabled() = true
    override fun getAuthorities() = listOf(SimpleGrantedAuthority("ROLE_${user.role.name}"))
}
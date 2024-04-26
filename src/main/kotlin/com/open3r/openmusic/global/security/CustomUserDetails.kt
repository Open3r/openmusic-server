package com.open3r.openmusic.global.security

import com.open3r.openmusic.domain.user.domain.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class CustomUserDetails(
    val user: User
) : UserDetails {
    override fun getAuthorities(): List<GrantedAuthority> = listOf(SimpleGrantedAuthority("ROLE_${user.role.name}"))
    override fun getPassword() = user.password
    override fun getUsername() = user.username
    override fun isAccountNonExpired() = true
    override fun isAccountNonLocked() = true
    override fun isCredentialsNonExpired() = true
    override fun isEnabled() = true
}
package com.open3r.openmusic.global.security

import com.open3r.openmusic.domain.user.domain.UserEntity
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class CustomUserDetails(val user: UserEntity) : UserDetails {
    override fun getAuthorities() = listOf(SimpleGrantedAuthority("ROLE_${user.role.name}"))
    override fun getPassword() = user.password
    override fun getUsername() = user.email
    override fun isAccountNonExpired() = true
    override fun isAccountNonLocked() = true
    override fun isCredentialsNonExpired() = true
    override fun isEnabled() = true
}
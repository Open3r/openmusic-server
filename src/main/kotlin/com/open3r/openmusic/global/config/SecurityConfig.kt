package com.open3r.openmusic.global.config

import com.open3r.openmusic.global.security.jwt.JwtAccessDeniedHandler
import com.open3r.openmusic.global.security.jwt.JwtAuthenticationEntryPoint
import com.open3r.openmusic.global.security.jwt.JwtAuthenticationFilter
import com.open3r.openmusic.global.security.jwt.JwtExceptionFilter
import com.open3r.openmusic.global.security.oauth.CustomOAuth2UserService
import com.open3r.openmusic.global.security.oauth.OAuth2SuccessHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
class SecurityConfig(
    private val jwtAccessDeniedHandler: JwtAccessDeniedHandler,
    private val jwtAuthenticationEntryPoint: JwtAuthenticationEntryPoint,
    private val jwtAuthenticationFilter: JwtAuthenticationFilter,
    private val jwtExceptionFilter: JwtExceptionFilter,
    private val customOAuth2DetailsService: CustomOAuth2UserService,
    private val oAuth2SuccessHandler: OAuth2SuccessHandler
) {
    @Bean
    fun passwordEncoder() = BCryptPasswordEncoder()

    @Bean
    fun roleHierarchy() = RoleHierarchyImpl().apply {
        setHierarchy("ROLE_ADMIN > ROLE_USER")
    }

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain = http
        .csrf { it.disable() }
        .cors { it.disable() }
        .httpBasic { it.disable() }
        .formLogin { it.disable() }
        .rememberMe { it.disable() }
        .logout { it.disable() }
        .oauth2Login {
            it
                .userInfoEndpoint { endpoint ->
                    endpoint.userService(customOAuth2DetailsService)
                }
                .successHandler(oAuth2SuccessHandler)
        }
        .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
        .exceptionHandling {
            it.accessDeniedHandler(jwtAccessDeniedHandler)
            it.authenticationEntryPoint(jwtAuthenticationEntryPoint)
        }
        .authorizeHttpRequests {
            it
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                .requestMatchers("/", "/**").permitAll()
                .anyRequest().authenticated()
        }
        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
        .addFilterBefore(jwtExceptionFilter, jwtAuthenticationFilter.javaClass)
        .build()
}

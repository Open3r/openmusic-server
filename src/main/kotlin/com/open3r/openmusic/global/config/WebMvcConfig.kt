package com.open3r.openmusic.global.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@EnableWebMvc
@Configuration
class WebMvcConfig : WebMvcConfigurer {
//    override fun addCorsMappings(registry: CorsRegistry) {
//        registry
//            .addMapping("/**")
//            .allowedOriginPatterns("*")
//            .allowedMethods("OPTIONS", "GET", "POST", "PUT", "PATCH", "DELETE")
//            .allowedHeaders("Content-Type", "Authorization")
//            .allowCredentials(true)
//            .maxAge(3600)
//    }
}
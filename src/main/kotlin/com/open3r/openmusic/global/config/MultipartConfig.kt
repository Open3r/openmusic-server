package com.open3r.openmusic.global.config

import jakarta.servlet.MultipartConfigElement
import org.springframework.boot.web.servlet.MultipartConfigFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.util.unit.DataSize
import org.springframework.web.multipart.MultipartResolver
import org.springframework.web.multipart.support.StandardServletMultipartResolver

@Configuration
class MultipartConfig {
    @Bean
    fun multipartResolver(): MultipartResolver = StandardServletMultipartResolver()

    @Bean
    fun multipartConfigElement(): MultipartConfigElement = MultipartConfigFactory().apply {
        setLocation("/Users/dgsw52/Projects/openmusic/files")
        setMaxRequestSize(DataSize.ofMegabytes(500L))
        setMaxFileSize(DataSize.ofMegabytes(500L))
    }.createMultipartConfig()
}
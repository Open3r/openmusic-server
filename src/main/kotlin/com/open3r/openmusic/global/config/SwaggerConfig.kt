package com.open3r.openmusic.global.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Contact
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders

@Configuration
class SwaggerConfig {
    @Bean
    fun api(): OpenAPI = OpenAPI()
        .info(
            Info().title("OpenMusic").description("OpenMusic API Documentation").version("v1.0")
                .contact(Contact().name("오프너").url("https://open3r.com").email("contact@open3r.com")).license(
                    License().name("MIT License").url("https://open3r.com")
                ).termsOfService("terms")
        )
        .addSecurityItem(SecurityRequirement().addList("Authorization"))
        .components(
            Components()
                .addSecuritySchemes(
                    "Authorization", SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("Authorization")
                        .`in`(SecurityScheme.In.HEADER)
                        .name(HttpHeaders.AUTHORIZATION)
                )
        )
}
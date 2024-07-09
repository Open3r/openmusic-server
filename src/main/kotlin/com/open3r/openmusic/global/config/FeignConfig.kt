package com.open3r.openmusic.global.config

import org.springframework.boot.autoconfigure.ImportAutoConfiguration
import org.springframework.cloud.netflix.feign.EnableFeignClients
import org.springframework.cloud.netflix.feign.FeignAutoConfiguration
import org.springframework.context.annotation.Configuration

@Configuration
@EnableFeignClients
@ImportAutoConfiguration(FeignAutoConfiguration::class)
class FeignConfig
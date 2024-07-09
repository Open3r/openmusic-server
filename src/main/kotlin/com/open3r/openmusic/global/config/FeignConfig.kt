package com.open3r.openmusic.global.config

import org.springframework.boot.autoconfigure.ImportAutoConfiguration
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.cloud.openfeign.FeignAutoConfiguration
import org.springframework.context.annotation.Configuration

@Configuration
@EnableFeignClients
@ImportAutoConfiguration(FeignAutoConfiguration::class)
class FeignConfig
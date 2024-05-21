package com.open3r.openmusic.global.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor

@EnableAsync
@Configuration
class AsyncConfig {
    @Bean
    fun taskExecutor() = ThreadPoolTaskExecutor().apply {
        corePoolSize = 10
        maxPoolSize = 20
        queueCapacity = 500

        setThreadNamePrefix("async-thread-")
        setAwaitTerminationMillis(60000)

        initialize()
    }
}
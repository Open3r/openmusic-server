package com.open3r.openmusic

import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@EnableJpaAuditing
@SpringBootApplication
@ConfigurationPropertiesScan
class OpenMusicApplication

fun main(args: Array<String>) {
    runApplication<OpenMusicApplication>(*args)
}

inline fun <reified T> T.logger() = LoggerFactory.getLogger(T::class.java)
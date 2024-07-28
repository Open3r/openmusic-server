package com.open3r.openmusic

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class OpenMusicApplication

fun main(args: Array<String>) {
    runApplication<OpenMusicApplication>(*args)
}

inline fun <reified T> T.logger(): Logger = LoggerFactory.getLogger(T::class.java)
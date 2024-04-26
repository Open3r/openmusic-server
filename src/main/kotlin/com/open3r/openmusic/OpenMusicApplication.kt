package com.open3r.openmusic

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class OpenMusicApplication

fun main(args: Array<String>) {
    runApplication<OpenMusicApplication>(*args)
}

package com.open3r.openmusic.domain.index.controller

import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/")
class IndexController {
    @Value("\${index}")
    private lateinit var index: String

    @GetMapping("/index")
    fun getIndex(): String {
        return index
    }
}
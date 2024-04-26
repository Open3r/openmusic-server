package com.open3r.openmusic.index.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/")
class IndexController {
    @GetMapping
    fun getIndex(): String {
        return "Index 1231231233"
    }
}
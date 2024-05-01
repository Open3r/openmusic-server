package com.open3r.openmusic.domain.song.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/songs")
class SongController {
    @GetMapping
    fun getSongs() {

    }
}
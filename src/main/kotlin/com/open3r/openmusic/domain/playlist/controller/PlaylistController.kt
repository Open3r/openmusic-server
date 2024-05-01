package com.open3r.openmusic.domain.playlist.controller

import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/playlists")
class PlaylistController {
    @GetMapping
    fun getPlaylists() {

    }

    @PostMapping
    fun createPlaylist() {

    }

    @DeleteMapping
    fun deletePlaylist() {

    }
}
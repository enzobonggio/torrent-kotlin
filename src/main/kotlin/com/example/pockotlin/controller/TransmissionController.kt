package com.example.pockotlin.controller

import com.example.pockotlin.dto.AddTorrentDTO
import com.example.pockotlin.model.Transmission
import com.example.pockotlin.service.TransmissionService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class TransmissionController {

    @Autowired
    private lateinit var transmissionService: TransmissionService

    @GetMapping("/torrents")
    suspend fun get(): List<Transmission.Torrent> {
        return transmissionService.getTorrents()
    }

    @PostMapping("/torrents")
    suspend fun add(@RequestBody torrent: AddTorrentDTO): Transmission.TorrentInfo? {
        return transmissionService.addTorrent(torrent.url)
    }
}
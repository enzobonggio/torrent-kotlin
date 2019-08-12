package com.example.pockotlin.controller

import com.example.pockotlin.dto.AddTorrentDTO
import com.example.pockotlin.model.Transmission
import com.example.pockotlin.service.TransmissionService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
class TransmissionController {

    @Autowired
    private lateinit var transmissionService: TransmissionService

    @GetMapping("/torrents")
    fun get(): Flux<Transmission.Torrent> {
        return transmissionService.getTorrents()
    }

    @PostMapping("/torrents")
    fun add(@RequestBody torrent: AddTorrentDTO): Mono<Transmission.TorrentInfo> {
        return transmissionService.addTorrent(torrent.url)
    }
}
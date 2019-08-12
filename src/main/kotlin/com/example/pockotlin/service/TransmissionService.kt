package com.example.pockotlin.service

import com.example.pockotlin.model.Transmission
import com.example.pockotlin.repository.TransmissionRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class TransmissionService(private val transmissionRepository: TransmissionRepository) {
    fun getTorrents(): Flux<Transmission.Torrent> {
        return transmissionRepository.get()
    }

    fun addTorrent(url: String): Mono<Transmission.TorrentInfo> {
        return transmissionRepository.add(url)
    }

}

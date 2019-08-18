package com.example.pockotlin.service

import com.example.pockotlin.model.Transmission

class TorrentService(private val transmissionService: TransmissionService) {

    suspend fun getTorrents() = transmissionService.getTorrents()
            .map { it.toTorrent() }

    suspend fun addTorrent(url: String): Transmission.TorrentInfo? =
            transmissionService.addTorrent(url)

    suspend fun deleteTorrent(id: Number) =
            transmissionService.deleteTorrent(id)
}

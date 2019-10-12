package com.example.pockotlin.service

import com.example.pockotlin.model.Transmission
import com.example.pockotlin.repository.TransmissionRepository

class TransmissionService(private val transmissionRepository: TransmissionRepository) {

    suspend fun getTorrents() = transmissionRepository.get()

    suspend fun addTorrent(url: String): Transmission.TorrentInfo =
            transmissionRepository.add(url)

    suspend fun deleteTorrent(id: Number) =
            transmissionRepository.delete(listOf(id))
}

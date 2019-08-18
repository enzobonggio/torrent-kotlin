package com.example.pockotlin.dto

data class TorrentDTO(
        val downloaded: Number?,
        val length: Number?,
        val name: String?,
        val downloadDir: String?,
        val peersConnected: Number?)
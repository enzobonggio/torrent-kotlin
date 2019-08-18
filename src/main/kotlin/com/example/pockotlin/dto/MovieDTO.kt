package com.example.pockotlin.dto

class MovieDTO {
    data class Info(
            val title: String,
            val year: Number?,
            val image: String?,
            val torrents: List<Torrent>?)

    data class Torrent(
            val url: String?,
            val quality: String?
    )
}
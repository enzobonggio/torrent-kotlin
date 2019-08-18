package com.example.pockotlin.model

import com.example.pockotlin.dto.MovieDTO
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

class Yifi {

    data class MovieResponse(
            val status: String?,
            val statusMessage: String?,
            val data: Data?,
            val meta: Meta?)

    data class Meta(
            val serverTime: Number?,
            val serverTimezone: String?,
            val apiVersion: Number?,
            val executionTime: String?)

    data class Data(
            val movieCount: Number?,
            val limit: Number?,
            val pageNumber: Number?,
            val movies: List<Movie>?)

    @Document("movie")
    data class Movie(
            @Id
            val id: Number,
            val url: String?,
            val imdbCode: String?,
            @Indexed
            val title: String,
            val titleEnglish: String?,
            val titleLong: String?,
            val slug: String?,
            val year: Number?,
            val rating: Float?,
            val runtime: Number?,
            val genres: List<String>?,
            val summary: String?,
            val descriptionFull: String?,
            val synopsis: String?,
            val ytTrailerCode: String?,
            val language: String?,
            val mpaRating: String?,
            val backgroundImage: String?,
            val backgroundImageOriginal: String?,
            val smallCoverImage: String?,
            val mediumCoverImage: String?,
            val largeCoverImage: String?,
            val state: String?,
            val torrents: List<Torrent>?,
            val dateUploaded: String?,
            val dateUploadedUnix: Number?,
            @Indexed
            var uniqueHash: Int) {

        init {
            this.uniqueHash = Objects.hash(this.id, this.title, this.torrents?.map { Pair(it.url, it.hash) })
        }

        fun toMovie() = MovieDTO.Info(
                title = title, year = year, image = mediumCoverImage, torrents = torrents?.map { it.toTorrent() }
        )

    }


    data class Torrent(
            val url: String?,
            val hash: String?,
            val quality: String?,
            val type: String?,
            val seeds: Number?,
            val peers: Number?,
            val size: String?,
            val sizeBytes: Number?,
            val dateUploaded: String?,
            val dateUploadedUnix: Number?) {
        fun toTorrent() = MovieDTO.Torrent(
                url = url, quality = quality
        )
    }
}
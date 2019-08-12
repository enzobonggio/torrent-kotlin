package com.example.pockotlin.model

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

class Yifi {
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    data class MovieResponse(
            @JsonProperty("status")
            val status: String?,
            @JsonProperty("status_message")
            val statusMessage: String?,
            @JsonProperty("data")
            val data: Data?,
            @JsonProperty("@meta")
            val meta: Meta?)

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    data class Meta(
            @JsonProperty("server_time")
            val serverTime: Number?,
            @JsonProperty("server_timezone")
            val serverTimezone: String?,
            @JsonProperty("api_version")
            val apiVersion: Number?,
            @JsonProperty("execution_time")
            val executionTime: String?)

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    data class Data(
            @JsonProperty("movie_count")
            val movieCount: Number?,
            @JsonProperty("limit")
            val limit: Number?,
            @JsonProperty("page_number")
            val pageNumber: Number?,
            @JsonProperty("movies")
            val movies: List<Movie>?) {}

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    data class Movie(
            @JsonProperty("id")
            val id: Number?,
            @JsonProperty("url")
            val url: String?,
            @JsonProperty("imdb_code")
            val imdbCode: String?,
            @JsonProperty("title")
            val title: String?,
            @JsonProperty("title_english")
            val titleEnglish: String?,
            @JsonProperty("title_long")
            val titleLong: String?,
            @JsonProperty("slug")
            val slug: String?,
            @JsonProperty("year")
            val year: Number?,
            @JsonProperty("rating")
            val rating: Float?,
            @JsonProperty("runtime")
            val runtime: Number?,
            @JsonProperty("genres")
            val genres: List<String>?,
            @JsonProperty("summary")
            val summary: String?,
            @JsonProperty("description_full")
            val descriptionFull: String?,
            @JsonProperty("synopsis")
            val synopsis: String?,
            @JsonProperty("yt_trailer_code")
            val ytTrailerCode: String?,
            @JsonProperty("language")
            val language: String?,
            @JsonProperty("mpa_rating")
            val mpaRating: String?,
            @JsonProperty("background_image")
            val backgroundImage: String?,
            @JsonProperty("background_image_original")
            val backgroundImageOriginal: String?,
            @JsonProperty("small_cover_image")
            val smallCoverImage: String?,
            @JsonProperty("medium_cover_image")
            val mediumCoverImage: String?,
            @JsonProperty("large_cover_image")
            val largeCoverImage: String?,
            @JsonProperty("state")
            val state: String?,
            @JsonProperty("torrents")
            val torrents: List<Torrent>?,
            @JsonProperty("date_uploaded")
            val dateUploaded: String?,
            @JsonProperty("date_uploaded_unix")
            val dateUploadedUnix: Number?)

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    data class Torrent(
            @JsonProperty("url")
            val url: String?,
            @JsonProperty("hash")
            val hash: String?,
            @JsonProperty("quality")
            val quality: String?,
            @JsonProperty("type")
            val type: String?,
            @JsonProperty("seeds")
            val seeds: Number?,
            @JsonProperty("peers")
            val peers: Number?,
            @JsonProperty("size")
            val size: String?,
            @JsonProperty("size_bytes")
            val sizeBytes: Number?,
            @JsonProperty("date_uploaded")
            val dateUploaded: String?,
            @JsonProperty("date_uploaded_unix")
            val dateUploadedUnix: Number?)
}
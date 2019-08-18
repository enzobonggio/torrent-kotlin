package com.example.pockotlin.handler

import com.example.pockotlin.service.MovieService
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyAndAwait
import org.springframework.web.reactive.function.server.buildAndAwait

class MovieHandler(private val movieService: MovieService) {

    @Suppress("UNUSED_PARAMETER")
    suspend fun get(request: ServerRequest): ServerResponse {
        return  ServerResponse.ok().bodyAndAwait(movieService.findAll())
    }

    @Suppress("UNUSED_PARAMETER")
    suspend fun post(request: ServerRequest): ServerResponse {
        movieService.historical()
        return ServerResponse.noContent().buildAndAwait()
    }
}
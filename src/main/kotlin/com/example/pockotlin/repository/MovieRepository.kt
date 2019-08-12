package com.example.pockotlin.repository

import com.example.pockotlin.model.Yifi
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Repository
class MovieRepository {

    @Autowired
    private lateinit var webclientBuilder: WebClient.Builder

    private val webClient: WebClient by lazy {
        webclientBuilder
                .clone()
                .baseUrl("https://yts.lt/api/v2/")
                .build()
    }


    fun get(limit: Int = 5, page: Int = 0): Mono<Yifi.MovieResponse> {
        return webClient.get()
                .uri("list_movies.json?limit={limit}&page={page}", mapOf("limit" to limit, "page" to page))
                .retrieve()
                .bodyToMono(Yifi.MovieResponse::class.java)
    }

}
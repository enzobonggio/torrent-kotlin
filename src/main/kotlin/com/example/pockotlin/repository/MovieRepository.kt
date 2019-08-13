package com.example.pockotlin.repository

import com.example.pockotlin.model.Yifi
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import org.springframework.web.reactive.function.client.awaitExchange

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


    suspend fun get(limit: Int = 5, page: Int = 0): Yifi.MovieResponse? {
        return webClient.get()
                .uri("list_movies.json?limit={limit}&page={page}", mapOf("limit" to limit, "page" to page))
                .awaitExchange()
                .awaitBody()
    }

}
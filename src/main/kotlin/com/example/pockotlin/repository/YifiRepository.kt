package com.example.pockotlin.repository

import com.example.pockotlin.model.Yifi
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.http.codec.json.Jackson2JsonDecoder
import org.springframework.http.codec.json.Jackson2JsonEncoder
import org.springframework.web.reactive.function.client.ExchangeStrategies
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import org.springframework.web.reactive.function.client.awaitExchange

class YifiRepository(objectMapper: ObjectMapper) {

    private val log = LoggerFactory.getLogger(YifiRepository::class.java)

    private val strategies by lazy {
        val mapper = objectMapper.copy()
        mapper.propertyNamingStrategy = PropertyNamingStrategy.SnakeCaseStrategy.SNAKE_CASE
        ExchangeStrategies.builder()
                .codecs { configurer ->
                    configurer.registerDefaults(false)
                    configurer.customCodecs().encoder(Jackson2JsonEncoder(mapper, APPLICATION_JSON))
                    configurer.customCodecs().decoder(Jackson2JsonDecoder(mapper, APPLICATION_JSON))
                }.build()
    }

    private val webClient: WebClient by lazy {
        WebClient.builder()
                .baseUrl("https://yts.lt/api/v2/")
                .exchangeStrategies(strategies)
                .filter { request, filter ->
                    log.info("Requested url {}", request.url())
                    filter.exchange(request)
                }
                .build()
    }


    suspend fun get(limit: Int = 5, page: Int = 1): Yifi.MovieResponse =
            webClient.get().uri("list_movies.json?limit={limit}&page={page}", mapOf("limit" to limit, "page" to page))
                    .awaitExchange()
                    .awaitBody<Yifi.MovieResponse>()
                    .also { log.debug("Response from http {}", it) }
}
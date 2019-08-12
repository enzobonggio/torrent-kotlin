package com.example.pockotlin.repository

import com.example.pockotlin.exception.ConflictException
import com.example.pockotlin.model.Transmission
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Repository
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Repository
class TransmissionRepository {

    @Autowired
    private lateinit var webclientBuilder: WebClient.Builder

    @Autowired
    private lateinit var mapper: ObjectMapper

    private var sessionId: String? = null

    private val webClient: WebClient by lazy {
        webclientBuilder
                .clone()
                .baseUrl("http://localhost:9091/transmission/rpc")
                .build()
    }


    fun get(): Flux<Transmission.Torrent> {
        return genericCall(Transmission.Request(
                mapOf<String, Any>("fields" to Transmission.Torrent::class.java.declaredFields.asList().map { it.name }),
                "torrent-get",
                1))
                .map { it.arguments }
                .map { it["torrents"] }
                .flatMapIterable { mapper.convertValue(it, Array<Transmission.Torrent>::class.java).asList() }
    }

    fun add(url: String): Mono<Transmission.TorrentInfo> {
        return genericCall(Transmission.Request(
                mapOf<String, Any>("filename" to url),
                "torrent-add",
                2))
                .map { it.arguments }
                .map { it["torrent-duplicate"]?.let { i -> Pair(true, i) } ?: Pair(false, it["torrent-added"]) }
                .map { mapper.convertValue(it.second, Transmission.TorrentInfo::class.java) }
    }

    fun genericCall(request: Transmission.Request): Mono<Transmission.Response> {
        return Mono.defer {
            webClient.post()
                    .body(BodyInserters.fromObject(request))
                    .header("X-Transmission-Session-Id", sessionId ?: "")
                    .retrieve()
                    .onStatus(
                            { status -> HttpStatus.CONFLICT == status },
                            { response -> Mono.error(ConflictException(response.headers().header("X-Transmission-Session-Id").firstOrNull())) })
                    .bodyToMono(Transmission.Response::class.java)
        }
                .retry { ex -> ex is ConflictException && setSessionId(ex.sessionId) }
    }

    private fun setSessionId(sessionId: String?): Boolean {
        this.sessionId = sessionId
        return sessionId != null
    }

}
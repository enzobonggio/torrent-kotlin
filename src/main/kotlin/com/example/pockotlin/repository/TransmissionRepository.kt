package com.example.pockotlin.repository

import com.example.pockotlin.model.Transmission
import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Repository
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.ClientResponse
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import org.springframework.web.reactive.function.client.awaitExchange
import java.lang.RuntimeException

@Repository
class TransmissionRepository {

    val log: Logger = LoggerFactory.getLogger(TransmissionRepository::class.java)


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


    suspend fun get(): List<Transmission.Torrent> {
        val torrents = genericCall(Transmission.Request(
                mapOf<String, Any>("fields" to Transmission.Torrent::class.java.declaredFields.asList().map { it.name }),
                "torrent-get",
                1))
                ?.arguments
                ?.get("torrents")

        return mapper.convertValue(torrents, Array<Transmission.Torrent>::class.java).asList()
    }

    suspend fun add(url: String): Transmission.TorrentInfo? {
        return genericCall(Transmission.Request(
                mapOf<String, Any>("filename" to url),
                "torrent-add",
                2))
                ?.arguments
                ?.let { Pair(true, it["torrent-duplicate"] ?: it.getValue("torrent-added")) }
                ?.let { mapper.convertValue(it.second, Transmission.TorrentInfo::class.java) }
    }

    suspend fun genericCall(request: Transmission.Request, retries: Int = 0): Transmission.Response? {
        val response = webClient.post()
                .body(BodyInserters.fromObject(request))
                .header("X-Transmission-Session-Id", sessionId ?: "")
                .awaitExchange()

        return when {
            checkIfRetry(response.statusCode(), retries) -> {
                response.headers()
                        .header("X-Transmission-Session-Id")
                        .firstOrNull()
                        ?.let {
                            this.sessionId = it
                            return genericCall(request)
                        }
            }
            checkError(response.statusCode()) -> {
                log.error("Error in response: {}", response)
                throw RuntimeException("Error in response")
            }
            else -> response.awaitBody()
        }
    }


    fun checkIfRetry(httpStatus: HttpStatus, retries: Int): Boolean {
        return httpStatus == HttpStatus.CONFLICT && retries < 5
    }

    fun checkError(httpStatus: HttpStatus): Boolean {
        return httpStatus.isError
    }


}
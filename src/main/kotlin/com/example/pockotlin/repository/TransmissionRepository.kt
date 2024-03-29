package com.example.pockotlin.repository

import com.example.pockotlin.model.Transmission
import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import org.springframework.web.reactive.function.client.awaitExchange

class TransmissionRepository(private val mapper: ObjectMapper) {

    private val log = LoggerFactory.getLogger(TransmissionRepository::class.java)
    private var sessionId: String? = null

    private val webClient: WebClient by lazy {
        WebClient.builder()
                .baseUrl("http://localhost:9091/transmission/rpc")
                .filter { request, filter ->
                    log.info("Requested url {}", request.url())
                    filter.exchange(request)
                }
                .build()
    }

    suspend fun get(): List<Transmission.Torrent> {
        val torrents = genericCall(Transmission.Request(
                mapOf<String, Any>("fields" to Transmission.Torrent::class.java.declaredFields.asList().map { it.name }),
                "torrent-get",
                1)).arguments
                ?.get("torrents")
        return mapper.convertValue(torrents, Array<Transmission.Torrent>::class.java).asList()
    }

    suspend fun delete(ids: List<Number>, deleteLocalData: Boolean = true) =
            genericCall(Transmission.Request(
                    mapOf("ids" to ids, "delete-local-data" to deleteLocalData),
                    "torrent-remove",
                    1
            ))

    suspend fun add(url: String): Transmission.TorrentInfo {
        val arguments = genericCall(Transmission.Request(
                mapOf<String, Any>("filename" to url),
                "torrent-add",
                2))
                .arguments ?: throw RuntimeException("Error in response")
        return arguments.let { Pair(true, it["torrent-duplicate"] ?: it.getValue("torrent-added")) }
                .let { mapper.convertValue(it.second, Transmission.TorrentInfo::class.java) }
    }

    private suspend fun genericCall(request: Transmission.Request, retries: Int = 0): Transmission.Response {
        val response = webClient.post()
                .body(BodyInserters.fromObject(request))
                .header("X-Transmission-Session-Id", sessionId ?: "")
                .awaitExchange()

        return when {
            checkIfRetry(response.statusCode(), retries) -> {
                this.sessionId = response.headers()
                        .header("X-Transmission-Session-Id")
                        .firstOrNull()
                genericCall(request)
            }
            checkError(response.statusCode()) -> {
                throw RuntimeException("Error in response")
            }
            else -> response.awaitBody()
        }
    }


    private fun checkIfRetry(httpStatus: HttpStatus, retries: Int): Boolean {
        return httpStatus == HttpStatus.CONFLICT && retries < 5
    }

    private fun checkError(httpStatus: HttpStatus): Boolean {
        return httpStatus.isError
    }


}
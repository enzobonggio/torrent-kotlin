package com.example.pockotlin.handler

import am.ik.yavi.fn.awaitFold
import am.ik.yavi.fn.awaitRightMap
import com.example.pockotlin.dto.AddTorrentDTO
import com.example.pockotlin.service.TorrentService
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.*

class TorrentHandler(private val torrentService: TorrentService) {

    @Suppress("UNUSED_PARAMETER")
    suspend fun get(request: ServerRequest) =
            ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                    .bodyAndAwait(torrentService.getTorrents())

    suspend fun post(request: ServerRequest): ServerResponse {
        return request.awaitBody<AddTorrentDTO>()
                .validate()
                .awaitRightMap { torrentService.addTorrent(it.url) }
                .awaitFold(
                        { ServerResponse.badRequest().bodyAndAwait(it) },
                        { ServerResponse.ok().bodyAndAwait(it) })
    }

    suspend fun delete(request: ServerRequest): ServerResponse {
        val id = request.pathVariable("id").toInt()
        torrentService.deleteTorrent(id)
        return ServerResponse.noContent().buildAndAwait()
    }
}
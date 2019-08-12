package com.example.pockotlin.model

import com.fasterxml.jackson.annotation.JsonInclude

class Transmission {
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    data class Torrent(
            val id: Number,
            val name: String,
            val totalSize: Number,
            val downloadDir: String,
            val leftUntilDone: Number)

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    data class Request(
            val arguments: Map<String, Any>,
            val method: String,
            val tag: Number
    )

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    data class Response(
            val arguments: Map<String, Any>,
            val result: String,
            val tag: Number
    )

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    data class TorrentInfo(
            val id: Number,
            val comment: String?,
            val creator: String?,
            val dateCreated: Number?,
            val hashString: String,
            val name: String,
            val pieceCount: Number?,
            val pieceSize: Number?,
            val totalSize: Number?,
            val torrentFile: Number?
    )
}
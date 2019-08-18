package com.example.pockotlin.model

import com.fasterxml.jackson.annotation.JsonProperty

class Transmission {
    data class Torrent(
            val activityDate: Number?,
            val addedDate: Number?,
            val bandwidthPriority: Number?,
            val comment: String?,
            val corruptEver: Number?,
            val creator: String?,
            val dateCreated: Number?,
            val desiredAvailable: Number?,
            val doneDate: Number?,
            val downloadDir: String?,
            val downloadedEver: Number?,
            val downloadLimit: Number?,
            val downloadLimited: Boolean?,
            val error: Number?,
            val errorString: String?,
            val eta: Number?,
            val etaIdle: Number?,
            val files: List<File>?,
            val fileStats: List<FileStat>?,
            val hashString: String?,
            val haveUnchecked: Number?,
            val haveValid: Number?,
            val honorsSessionLimits: Boolean?,
            val id: Number?,
            val isFinished: Boolean?,
            val isPrivate: Boolean?,
            val isStalled: Boolean?,
            val labels: List<Label>?,
            val leftUntilDone: Number?,
            val magnetLink: String?,
            val manualAnnounceTime: Number?,
            val maxConnectedPeers: Number?,
            val metadataPercentComplete: Number?,
            val name: String?,
            @JsonProperty("peer-limit") val peerLimit: Number?,
            val peers: List<Peer>?,
            val peersConnected: Number?,
            val peersFrom: PeersFrom,
            val peersGettingFromUs: Number?,
            val peersSendingToUs: Number?,
            val percentDone: Number?,
            val pieces: String?,
            val pieceCount: Number?,
            val pieceSize: Number?,
            //val priorities: List<Priority>,
            val queuePosition: Number?,
            val rateDownload: Number?,
            val rateUpload: Number?,
            val recheckProgress: Number?,
            val secondsDownloading: Number?,
            val secondsSeeding: Number?,
            val seedIdleLimit: Number?,
            val seedIdleMode: Number?,
            val seedRatioLimit: Number?,
            val seedRatioMode: Number?,
            val sizeWhenDone: Number?,
            val startDate: Number?,
            val status: Number?,
            //val trackers: List<Tracker>,
            //val trackerStats: List<TrackerStat>,
            val totalSize: Number?,
            val torrentFile: String?,
            val uploadedEver: Number?,
            val uploadLimit: Number?,
            val uploadLimited: Boolean?,
            val uploadRatio: Number?,
            //val wanted: List<Wanted>,
            //val webseeds: List<WebSeed>,
            val webseedsSendingToUs: Number?)

    data class File(
            val bytesCompleted: Number?,
            val length: Number?,
            val name: String?
    )

    data class FileStat(
            val bytesCompleted: Number?,
            val wanted: Boolean?,
            val priority: Number?
    )

    data class Label(val label: String?)

    data class Peer(
            val address: String?,
            val clientName: String?,
            val clientIsChoked: Boolean?,
            val clientIsInterested: Boolean?,
            val flagStr: String?,
            val isDownloadingFrom: Boolean?,
            val isEncrypted: Boolean?,
            val isIncoming: Boolean?,
            val isUploadingTo: Boolean?,
            val isUTP: Boolean?,
            val peerIsChoked: Boolean?,
            val peerIsInterested: Boolean?,
            val port: Number?,
            val progress: Number?,
            val rateToClient: Number?,
            val rateToPeer: Number?
    )

    data class PeersFrom(
            val fromCache: Number,
            val fromDht: Number,
            val fromIncoming: Number,
            val fromLpd: Number,
            val fromLtep: Number,
            val fromPex: Number,
            val fromTracker: Number
    )

    data class Request(
            val arguments: Map<String, Any>,
            val method: String,
            val tag: Number?
    )

    data class Response(
            val arguments: Map<String, Any>,
            val result: String,
            val tag: Number?
    )

    data class TorrentInfo(
            val id: Number,
            val comment: String?,
            val creator: String?,
            val dateCreated: Number?,
            val hashString: String?,
            val name: String,
            val pieceCount: Number?,
            val pieceSize: Number?,
            val totalSize: Number?,
            val torrentFile: Number?
    )
}
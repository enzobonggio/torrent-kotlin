package com.example.pockotlin.route

import com.example.pockotlin.handler.MovieHandler
import com.example.pockotlin.handler.TorrentHandler
import org.springframework.web.reactive.function.server.coRouter

fun routes(movieHandler: MovieHandler, torrentHandler: TorrentHandler) = coRouter {
    GET("/movies", movieHandler::get)
    POST("/movies", movieHandler::post)
    GET("/torrents", torrentHandler::get)
    POST("/torrents", torrentHandler::post)
    DELETE("/torrents/{id}", torrentHandler::delete)
}
package com.example.pockotlin.controller

import com.example.pockotlin.model.Yifi
import com.example.pockotlin.service.MovieService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class MovieController {

    @Autowired
    private lateinit var movieService: MovieService

    @GetMapping("/movies")
    suspend fun get2(): List<Yifi.Movie> {
        return movieService.get()
    }
}
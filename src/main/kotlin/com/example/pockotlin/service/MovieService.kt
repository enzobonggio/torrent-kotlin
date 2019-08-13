package com.example.pockotlin.service

import com.example.pockotlin.model.Yifi
import com.example.pockotlin.repository.MovieRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class MovieService {

    @Autowired
    lateinit var movieRepository: MovieRepository

    suspend fun get(): List<Yifi.Movie> {
        return movieRepository.get()?.data?.movies ?: emptyList()
    }
}
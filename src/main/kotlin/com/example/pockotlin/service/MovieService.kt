package com.example.pockotlin.service

import com.example.pockotlin.model.Yifi
import com.example.pockotlin.repository.MovieRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
class MovieService {

    @Autowired
    lateinit var movieRepository: MovieRepository

    fun get(): Flux<Yifi.Movie> {
        return movieRepository.get().flatMapIterable { it.data?.movies }
    }
}
package com.example.pockotlin.service

import com.example.pockotlin.dto.MovieDTO
import com.example.pockotlin.repository.MovieRepository
import org.slf4j.LoggerFactory

class MovieService(
        private val movieRepository: MovieRepository,
        private val yifiService: YifiService) {

    private val log = LoggerFactory.getLogger(MovieService::class.java)

    suspend fun findAll(): List<MovieDTO.Info> {
        return movieRepository.findAll().map { it.toMovie() }
    }

    suspend fun historical() {
        for (i in 1..100) {
            val movies = yifiService.get(page = i, limit = 50)
            val existMovies = movieRepository.findAllById(movies.map { it.id })
                    .map { it.id to it }
                    .toMap()
            val groupMovies = movies.groupBy { existMovies.contains(it.id) }
            groupMovies[true]
                    ?.filter { it.uniqueHash != existMovies.getValue(it.id).uniqueHash }
                    ?.also { log.info("Will update ids {}", it.map { m -> m.id }) }
                    ?.map {
                        movieRepository.update(it)
                    }
            groupMovies[false]?.let {
                log.info("Will insert ids {}", it.map { m -> m.id })
                movieRepository.insertAll(it)
            }
        }
    }
}
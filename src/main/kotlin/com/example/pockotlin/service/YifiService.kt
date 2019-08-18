package com.example.pockotlin.service;

import com.example.pockotlin.model.Yifi
import com.example.pockotlin.repository.YifiRepository
import org.slf4j.LoggerFactory

class YifiService(private val yifiRepository: YifiRepository) {

    private val log = LoggerFactory.getLogger(YifiService::class.java)

    suspend fun get(page: Int = 1, limit: Int = 5): List<Yifi.Movie> {
        val result = yifiRepository.get(page = page, limit = limit)
        return result?.data?.movies ?: emptyList()
    }
}

package com.example.pockotlin.repository

import com.example.pockotlin.model.Yifi.Movie
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.data.mongodb.core.*
import org.springframework.data.mongodb.core.query.Criteria.where
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Query.query
import org.springframework.data.mongodb.core.query.isEqualTo

class MovieRepository(private val mongo: ReactiveFluentMongoOperations) {

    suspend fun count() = mongo.query<Movie>().awaitCount()

    suspend fun findAll() = mongo
            .query<Movie>().matching(Query().limit(10).skip(0))
            .all()
//            .sort()
            .collectList().awaitSingle()

    suspend fun findOne(id: String) = mongo.query<Movie>().matching(query(where("id").isEqualTo(id))).awaitOne()

    suspend fun deleteAll() = mongo.remove<Movie>().allAndAwait()

    suspend fun insert(user: Movie) = mongo.insert<Movie>().oneAndAwait(user)

    suspend fun update(user: Movie) = mongo.update<Movie>().replaceWith(user).asType<Movie>().findReplaceAndAwait()!!

    suspend fun insertAll(movies: Collection<Movie>) = mongo.insert<Movie>().all(movies).collectList().awaitSingle()

    suspend fun findAllByHash(hashes: Collection<Int>) = mongo.query<Movie>().matching(query(where("uniqueHash").`in`(hashes))).all()
            .collectList()
            .awaitSingle()


    suspend fun findAllById(ids: Collection<Number>) = mongo.query<Movie>().matching(query(where("id").`in`(ids)))
            .all()
            .collectList()
            .awaitSingle()

    suspend fun findByTitle(title: String) = mongo.query<Movie>().matching(query(where("title").isEqualTo(title)))
            .awaitOne()

}
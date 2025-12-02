package com.example.test_lab_week_13

import com.example.test_lab_week_13.api.MovieService

import com.example.test_lab_week_13.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class MovieRepository(private val movieService: MovieService) {
    private val apiKey = "d152610732b74521c005a57c1daf8e75"
    fun fetchMovies(): Flow<List<Movie>> = flow {
        val response = movieService.getPopularMovies(apiKey)
        emit(response.results)
    }.flowOn(Dispatchers.IO)
}
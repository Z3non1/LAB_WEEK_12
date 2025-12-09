package com.example.lab_week_13

import com.example.lab_week_13.api.MovieService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class MovieRepository(private val movieService: MovieService) {

    private val apiKey = "7083020efe09f3ec46b7dc608d4fe2bf"

    fun fetchMovies(): Flow<List<Movie>> {
        // Tambahkan <List<Movie>> agar tipe data jelas (memperbaiki error inference)
        return flow<List<Movie>> {

            // response sekarang bertipe PopularMoviesResponse
            val response = movieService.getPopularMovies(apiKey)

            // PopularMoviesResponse punya properti 'results', jadi ini VALID
            val movies = response.results

            emit(movies)
        }.flowOn(Dispatchers.IO)
    }
}

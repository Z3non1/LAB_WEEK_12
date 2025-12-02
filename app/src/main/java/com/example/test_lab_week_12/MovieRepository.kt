package com.example.test_lab_week_12

import com.example.test_lab_week_12.api.MovieService
import com.example.test_lab_week_12.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow    // Import Wajib
import kotlinx.coroutines.flow.flow    // Import Wajib
import kotlinx.coroutines.flow.flowOn  // Import Wajib

class MovieRepository(private val movieService: MovieService) {

    private val apiKey = "7083020efe09f3ec46b7dc608d4fe2bf"

    fun fetchMovies(): Flow<List<Movie>> {
        return flow {
            // Mengambil data dari API
            val response = movieService.getPopularMovies(apiKey)

            // Mengecek apakah response body (hasilnya) ada atau tidak
            // response.results mungkin null jika API gagal, jadi kita handle dengan aman
            val movies = response.body()?.results ?: emptyList()

            emit(movies)
        }.flowOn(Dispatchers.IO)
    }
}

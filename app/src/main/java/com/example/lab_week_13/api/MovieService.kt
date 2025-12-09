package com.example.lab_week_13.api

// Import class response Anda yang sudah ada
import com.example.lab_week_13.model.PopularMoviesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {
    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String
    ): PopularMoviesResponse // <-- Pastikan ini pakai PopularMoviesResponse
}

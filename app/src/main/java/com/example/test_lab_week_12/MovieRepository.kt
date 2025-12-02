package com.example.test_lab_week_12

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.test_lab_week_12.api.MovieService
import com.example.test_lab_week_12.model.Movie // <--- TAMBAHKAN IMPORT INI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieRepository(private val movieService: MovieService) {

    private val apiKey = "7083020efe09f3ec46b7dc608d4fe2bf"

    // Menggunakan tipe data List<Movie>
    private val movieLiveData = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>>
        get() = movieLiveData

    private val errorLiveData = MutableLiveData<String>()
    val error: LiveData<String>
        get() = errorLiveData

    suspend fun fetchMovies() {
        try {
            // Panggil API di thread IO
            val response = withContext(Dispatchers.IO) {
                movieService.getPopularMovies(apiKey)
            }

            if (response.isSuccessful) {
                val body = response.body() // Ini tipe datanya PopularMoviesResponse?
                if (body != null) {
                    // Pastikan 'results' ada di dalam PopularMoviesResponse dan bertipe List<Movie>
                    movieLiveData.postValue(body.results)
                } else {
                    errorLiveData.postValue("No data received from server")
                }
            } else {
                errorLiveData.postValue("API error: ${response.code()} - ${response.message()}")
            }

        } catch (exception: Exception) {
            errorLiveData.postValue("An error occurred: ${exception.message}")
        }
    }
}

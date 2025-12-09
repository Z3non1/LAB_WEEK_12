package com.example.lab_week_13

import com.example.lab_week_13.api.MovieService
import com.example.lab_week_13.database.MovieDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import android.util.Log

class MovieRepository(
    private val movieService: MovieService,
    private val movieDatabase: MovieDatabase
) {
    private val apiKey = "7083020efe09f3ec46b7dc608d4fe2bf"

    // --- PERBAIKAN 1: Pindahkan fungsi network ke sini ---
    /**
     * Mengambil data film dari API dan menyimpannya ke database.
     * Dibuat sebagai suspend fun agar bisa dipanggil dari CoroutineWorker.
     */
    suspend fun refreshMovies() {
        try {
            val popularMovies = movieService.getPopularMovies(apiKey)
            val moviesFetched = popularMovies.results
            // Simpan ke database
            movieDatabase.movieDao().insertMovies(moviesFetched)
            Log.d("MovieRepository", "Movies refreshed from network and saved to DB.")
        } catch (exception: Exception) {
            Log.e(
                "MovieRepository",
                "Failed to refresh movies: ${exception.message}"
            )
        }
    }

    /**
     * Fungsi yang digunakan oleh ViewModel untuk menampilkan data.
     * Mengambil data dari database terlebih dahulu.
     * Jika database kosong, panggil refreshMovies() untuk mengisi data awal.
     */
    fun getMoviesFlow(): Flow<List<Movie>> {
        return flow {
            val movieDao = movieDatabase.movieDao()
            val savedMovies = movieDao.getAllMovies().first()

            if (savedMovies.isEmpty()) {
                // Jika database kosong, panggil fungsi refresh untuk pertama kali
                refreshMovies()
            }

            // Selalu emit data dari database sebagai sumber kebenaran (Single Source of Truth)
            // `getAllMovies()` mengembalikan Flow, jadi kita bisa langsung emit semua perubahannya
            emit(movieDao.getAllMovies().first()) // Emit data awal
            movieDao.getAllMovies().collect { updatedMovies -> // Terus pantau perubahan
                emit(updatedMovies)
            }

        }.flowOn(Dispatchers.IO)
    }
}

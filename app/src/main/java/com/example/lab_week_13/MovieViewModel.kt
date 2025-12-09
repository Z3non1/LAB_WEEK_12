package com.example.lab_week_13

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class MovieViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    // StateFlow untuk menyimpan list movie
    private val _popularMovies = MutableStateFlow<List<Movie>>(emptyList())
    val popularMovies: StateFlow<List<Movie>> = _popularMovies

    // StateFlow untuk menyimpan pesan error
    private val _error = MutableStateFlow("")
    val error: StateFlow<String> = _error

    init {
        // Ganti nama fungsi di sini juga jika ada
        fetchPopularMovies()
    }

    private fun fetchPopularMovies() {
        // viewModelScope sudah berjalan di Main thread, jadi tidak perlu Dispatchers.IO
        viewModelScope.launch {
            // --- PERBAIKAN DI SINI ---
            // Panggil fungsi getMoviesFlow() yang baru
            movieRepository.getMoviesFlow()
                .catch { e ->
                    _error.value = "An exception occurred: ${e.message}"
                }
                .collect { movies ->
                    _popularMovies.value = movies
                }
        }
    }
}

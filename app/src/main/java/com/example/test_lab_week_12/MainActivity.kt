package com.example.test_lab_week_12

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.RecyclerView
import com.example.test_lab_week_12.model.Movie
import com.example.test_lab_week_12.model.MovieAdapter
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import java.util.Calendar

class MainActivity : AppCompatActivity() {

    // Deklarasikan ViewModel di sini agar bisa diakses di seluruh class
    private lateinit var movieViewModel: MovieViewModel
    private lateinit var recyclerView: RecyclerView

    private val movieAdapter by lazy {
        MovieAdapter(object : MovieAdapter.MovieClickListener {
            override fun onMovieClick(movie: Movie) {
                openMovieDetails(movie)
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 1. Inisialisasi RecyclerView
        recyclerView = findViewById(R.id.movie_list)
        recyclerView.adapter = movieAdapter

        // 2. Inisialisasi ViewModel
        val movieRepository = (application as MovieApplication).movieRepository
        movieViewModel = ViewModelProvider(
            this,
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    @Suppress("UNCHECKED_CAST")
                    return MovieViewModel(movieRepository) as T
                }
            }
        )[MovieViewModel::class.java]

        // 3. Pindahkan blok observing ke dalam onCreate
        observeMovies()
    }

    private fun observeMovies() {
        // Menggunakan repeatOnLifecycle untuk mengamati StateFlow dengan aman
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                // Coroutine untuk mengamati movie list
                launch {
                    movieViewModel.popularMovies.collect { movies ->
                        // Logika filter dipindahkan ke sini
                        val currentYear = Calendar.getInstance().get(Calendar.YEAR).toString()
                        val filteredMovies = movies
                            .filter { it.releaseDate?.startsWith(currentYear) == true }
                            .sortedByDescending { it.popularity }
                        movieAdapter.addMovies(filteredMovies)
                    }
                }

                // Coroutine untuk mengamati error
                launch {
                    movieViewModel.error.collect { error ->
                        if (error.isNotEmpty()) {
                            Snackbar.make(recyclerView, error, Snackbar.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }
    }

    private fun openMovieDetails(movie: Movie) {
        val intent = Intent(this, MovieDetailActivity::class.java)
        intent.putExtra("movie", movie)
        startActivity(intent)
    }
}

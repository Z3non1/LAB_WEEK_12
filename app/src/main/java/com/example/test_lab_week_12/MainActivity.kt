package com.example.test_lab_week_12

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.test_lab_week_12.model.Movie
import com.example.test_lab_week_12.model.MovieAdapter
import com.google.android.material.snackbar.Snackbar
import java.util.Calendar

class MainActivity : AppCompatActivity() {

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

        val recyclerView: RecyclerView = findViewById(R.id.movie_list)
        recyclerView.adapter = movieAdapter

        // Pastikan MovieApplication sudah memiliki properti movieRepository
        val movieRepository = (application as MovieApplication).movieRepository

        val movieViewModel = ViewModelProvider(
            this,
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    @Suppress("UNCHECKED_CAST")
                    return MovieViewModel(movieRepository) as T
                }
            }
        )[MovieViewModel::class.java]

        // PERBAIKAN UTAMA DI SINI:
        // 'movies' di sini sudah berupa List<Movie>, bukan Response wrapper lagi.
        movieViewModel.popularMovies.observe(this) { movies ->
            val currentYear = Calendar.getInstance().get(Calendar.YEAR).toString()

            val filteredMovies = movies // Langsung gunakan 'movies'
                .filter { movie ->
                    // Pastikan releaseDate tidak null sebelum mengecek startsWith
                    movie.releaseDate?.startsWith(currentYear) == true
                }
                .sortedByDescending { it.popularity }

            movieAdapter.addMovies(filteredMovies)
        }

        movieViewModel.error.observe(this) { error ->
            if (!error.isNullOrEmpty()) {
                Snackbar.make(recyclerView, error, Snackbar.LENGTH_LONG).show()
            }
        }
    }

    private fun openMovieDetails(movie: Movie) {
        val intent = Intent(this, MovieDetailActivity::class.java)
        intent.putExtra("movie", movie)
        startActivity(intent)
    }
}

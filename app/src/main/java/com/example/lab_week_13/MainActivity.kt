package com.example.lab_week_13

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.RecyclerView
import com.example.lab_week_13.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import java.util.Calendar
import com.example.lab_week_13.MovieApplication
import com.example.lab_week_13.model.MovieDetailActivity


class MainActivity : AppCompatActivity() {

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

        // 1. Setup Binding
        val binding: ActivityMainBinding = DataBindingUtil
            .setContentView(this, R.layout.activity_main)

        // 2. Inisialisasi RecyclerView
        recyclerView = binding.movieList
        recyclerView.adapter = movieAdapter

        // 3. Inisialisasi ViewModel
        // Pastikan MovieApplication dan MovieRepository sudah benar
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

        // Binding ViewModel ke XML
        binding.viewModel = movieViewModel
        binding.lifecycleOwner = this

        // 4. Observe Data
        observeMovies()
    }

    private fun observeMovies() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                // Observe List Film
                launch {
                    movieViewModel.popularMovies.collect { movies ->
                        val currentYear = Calendar.getInstance().get(Calendar.YEAR).toString()

                        // Filter Logic: Ambil tahun sekarang & sort by popularity
                        val filteredMovies = movies
                            .filter { it.releaseDate?.startsWith(currentYear) == true }
                            .sortedByDescending { it.popularity }

                        // Update Adapter
                        movieAdapter.addMovies(filteredMovies)
                    }
                }

                // Observe Error
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
        // Class ini dikenali sekarang karena sudah di-import di atas
        val intent = Intent(this, MovieDetailActivity::class.java)
        intent.putExtra("movie", movie)
        startActivity(intent)
    }
}

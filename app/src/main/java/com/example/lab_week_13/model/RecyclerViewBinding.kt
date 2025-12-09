package com.example.lab_week_13.model

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.lab_week_13.Movie        // 1. Import Movie dari package utama
import com.example.lab_week_13.MovieAdapter // 2. Import Adapter dari package utama

@BindingAdapter("list")
fun bindMovies(view: RecyclerView, movies: List<Movie>?) {
    // 3. Gunakan Safe Cast (as?) agar aplikasi tidak crash jika adapter belum siap/null
    val adapter = view.adapter as? MovieAdapter

    if (adapter != null && movies != null) {
        adapter.addMovies(movies)
    }
}

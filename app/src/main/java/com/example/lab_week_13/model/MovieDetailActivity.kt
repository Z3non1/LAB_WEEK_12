package com.example.lab_week_13.model // 1. Sesuaikan package dengan nama folder

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.lab_week_13.Movie // 2. PENTING: Import Movie dari package utama
import com.example.lab_week_13.R     // 3. PENTING: Import R agar ID layout terbaca

class MovieDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        // Menerima data Movie dari Intent
        @Suppress("DEPRECATION")
        val movie = intent.getParcelableExtra<Movie>("movie")

        if (movie != null) {
            val posterView: ImageView = findViewById(R.id.detail_poster)
            val titleView: TextView = findViewById(R.id.detail_title)
            val dateView: TextView = findViewById(R.id.detail_release_date)
            val ratingView: TextView = findViewById(R.id.detail_rating)
            val overviewView: TextView = findViewById(R.id.detail_overview)

            titleView.text = movie.title
            dateView.text = "Release Date: ${movie.releaseDate}"
            ratingView.text = "Rating: ${movie.voteAverage}"
            overviewView.text = movie.overview

            Glide.with(this)
                .load("https://image.tmdb.org/t/p/w500${movie.posterPath}")
                .placeholder(android.R.drawable.ic_menu_gallery) // Tambahan: placeholder
                .into(posterView)
        }
    }
}

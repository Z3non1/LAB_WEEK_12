package com.example.test_lab_week_12

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.test_lab_week_12.model.Movie

class MovieDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        // Menerima data Movie dari Intent
        // Gunakan getParcelableExtra. Untuk API 33+ ada cara baru, tapi ini cara standar yang kompatibel.
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
                .into(posterView)
        }
    }
}

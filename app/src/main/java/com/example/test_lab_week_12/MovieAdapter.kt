package com.example.test_lab_week_12.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.test_lab_week_12.R // Pastikan ini tidak merah

class MovieAdapter(
    private val listener: MovieClickListener
) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    private val movies = mutableListOf<Movie>()

    interface MovieClickListener {
        fun onMovieClick(movie: Movie)
    }

    fun addMovies(newMovies: List<Movie>) {
        movies.clear()
        movies.addAll(newMovies)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        // Pastikan file 'res/layout/item_movie.xml' sudah dibuat
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    override fun getItemCount(): Int = movies.size

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        // Pastikan ID ini sesuai dengan yang ada di item_movie.xml
        private val titleView: TextView = itemView.findViewById(R.id.movie_title)
        private val posterView: ImageView = itemView.findViewById(R.id.movie_poster)

        fun bind(movie: Movie) {
            titleView.text = movie.title

            Glide.with(itemView.context)
                .load("https://image.tmdb.org/t/p/w500${movie.posterPath}")
                // .placeholder(R.drawable.placeholder) // Hapus comment ini HANYA jika file placeholder ada di drawable
                .into(posterView)

            itemView.setOnClickListener {
                listener.onMovieClick(movie)
            }
        }
    }
}

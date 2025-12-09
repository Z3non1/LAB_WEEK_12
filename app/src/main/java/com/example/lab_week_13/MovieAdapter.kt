package com.example.lab_week_13

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


class MovieAdapter(
    private val listener: MovieClickListener
) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    private val movies = mutableListOf<Movie>()

    // Interface untuk menangani klik
    interface MovieClickListener {
        fun onMovieClick(movie: Movie)
    }

    // Fungsi untuk mengupdate data di adapter
    fun addMovies(newMovies: List<Movie>) {
        movies.clear()
        movies.addAll(newMovies)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        // Layout item_movie harus ada di folder res/layout/
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    override fun getItemCount(): Int = movies.size

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        // Menghubungkan View dengan ID di layout item_movie.xml
        private val titleView: TextView = itemView.findViewById(R.id.movie_title)
        private val posterView: ImageView = itemView.findViewById(R.id.movie_poster)

        fun bind(movie: Movie) {
            titleView.text = movie.title

            // Menggunakan Glide untuk memuat gambar
            Glide.with(itemView.context)
                .load("https://image.tmdb.org/t/p/w500${movie.posterPath}")
                .placeholder(android.R.drawable.ic_menu_gallery) // Placeholder bawaan Android sementara
                .error(android.R.drawable.stat_notify_error) // Icon error bawaan
                .into(posterView)

            // Menangani event klik item
            itemView.setOnClickListener {
                listener.onMovieClick(movie)
            }
        }
    }
}

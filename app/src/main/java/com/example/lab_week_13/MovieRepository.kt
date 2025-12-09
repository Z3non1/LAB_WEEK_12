package com.example.lab_week_13

import com.example.lab_week_13.Movie
import com.example.lab_week_13.api.MovieService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import com.example.lab_week_13.database.MovieDatabase
import com.example.lab_week_13.database.MovieDao
import kotlinx.coroutines.flow.first

class MovieRepository(
    private val movieService: MovieService,
    private val movieDatabase: MovieDatabase
) {
    private val apiKey = "7083020efe09f3ec46b7dc608d4fe2bf"

    fun fetchMovies(): Flow<List<Movie>> {
        return flow {
            // Check if there are movies saved in the database
            val movieDao: MovieDao = movieDatabase.movieDao()
            val savedMovies = movieDao.getAllMovies().first()

            // If there are no movies saved in the database,
            // fetch the list of popular movies from the API
            if(savedMovies.isEmpty()) {
                val movies = movieService.getPopularMovies(apiKey).results
                // save the list of popular movies to the database
                movieDao.insertMovies(movies) // or whatever the actual name is in MovieDao
                // emit the list of popular movies from the API
                emit(movies)
            } else {
                // If there are movies saved in the database,
                // emit the list of saved movies from the database
                emit(savedMovies)
            }
        }.flowOn(Dispatchers.IO)
    }
}

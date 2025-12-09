package com.example.lab_week_13

import android.app.Application
import com.example.lab_week_13.api.MovieService
import com.example.lab_week_13.database.MovieDatabase
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MovieApplication : Application() {

    // 1. Initialize Retrofit Service
    private val movieService: MovieService by lazy {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

        retrofit.create(MovieService::class.java)
    }

    // 2. Initialize Database Instance
    private val movieDatabase: MovieDatabase by lazy {
        MovieDatabase.getInstance(this)
    }

    // 3. Initialize Repository
    // PERBAIKAN: Kirim 'movieDatabase' (bukan .movieDao()) karena Repository Anda memintanya demikian.
    val movieRepository: MovieRepository by lazy {
        MovieRepository(movieService, movieDatabase)
    }

    override fun onCreate() {
        super.onCreate()
    }
}

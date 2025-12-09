package com.example.lab_week_13

import android.app.Application
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.example.lab_week_13.api.MovieService
import com.example.lab_week_13.database.MovieDatabase
import com.example.lab_week_13.workers.MovieWorker // PENTING: Import MovieWorker

import com.squareup.moshi.Moshi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

class MovieApplication : Application() {

    // 1. Initialize Retrofit Service
    private val movieService: MovieService by lazy {
        // PERBAIKAN: Hapus .add(KotlinJsonAdapterFactory())
        // Karena kita pakai KSP (Codegen), Moshi akan otomatis menemukan adapter yang dibuat saat compile.
        val moshi = Moshi.Builder()
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
    val movieRepository: MovieRepository by lazy {
        MovieRepository(movieService, movieDatabase)
    }

    override fun onCreate() {
        super.onCreate()
        // Panggil fungsi untuk setup background work agar onCreate lebih rapi
        setupRecurringWork()
    }

    /**
     * Menjadwalkan tugas background yang berjalan setiap 1 jam
     * untuk mengambil data film jika perangkat terhubung ke internet.
     */
    private fun setupRecurringWork() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val repeatingRequest = PeriodicWorkRequest
            .Builder(MovieWorker::class.java, 1, TimeUnit.HOURS)
            .setConstraints(constraints)
            .addTag("movie-work")
            .build()

        WorkManager.getInstance(applicationContext).enqueue(repeatingRequest)
    }
}

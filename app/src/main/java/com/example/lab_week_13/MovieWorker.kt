package com.example.lab_week_13.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.lab_week_13.MovieApplication
import java.io.IOException

/**
 * A Worker class that fetches movies from the network in the background.
 */
class MovieWorker(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {

    companion object {
        const val TAG = "MovieWorker"
    }

    /**
     * This method is where the actual background work is performed.
     */
    override suspend fun doWork(): Result {
        // Get the repository from the Application class
        val repository = (applicationContext as MovieApplication).movieRepository

        return try {
            // Fetch the latest movies from the repository
            repository.refreshMovies()
            Log.d(TAG, "Work request to refresh movies successful")
            Result.success()
        } catch (e: IOException) {
            Log.e(TAG, "Work request failed with an exception", e)
            Result.failure()
        } catch (e: Exception) {
            Log.e(TAG, "An unknown error occurred during work", e)
            Result.failure()
        }
    }
}
    
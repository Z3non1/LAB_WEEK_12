package com.example.lab_week_13 // Pastikan package ini di root package

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Movie(
    val id: Int,
    val title: String,

    @Json(name = "poster_path")
    val posterPath: String?,

    val overview: String?,

    @Json(name = "vote_average")
    val voteAverage: Double?,

    @Json(name = "release_date")
    val releaseDate: String?,

    val popularity: Double
) : Parcelable

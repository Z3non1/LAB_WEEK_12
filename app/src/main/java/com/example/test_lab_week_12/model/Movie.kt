package com.example.test_lab_week_12.model

import android.os.Parcelable // 1. Import ini
import kotlinx.parcelize.Parcelize // 2. Import ini
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@Parcelize // 3. Tambahkan anotasi ini
data class Movie(
    @Json(name = "id") val id: Int,
    @Json(name = "title") val title: String,
    @Json(name = "overview") val overview: String,
    @Json(name = "poster_path") val posterPath: String?,
    @Json(name = "backdrop_path") val backdropPath: String?,
    @Json(name = "release_date") val releaseDate: String?,
    @Json(name = "vote_average") val voteAverage: Double,
    @Json(name = "popularity") val popularity: Double
) : Parcelable // 4. Implementasikan interface ini

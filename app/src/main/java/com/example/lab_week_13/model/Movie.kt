package com.example.lab_week_13

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
@Entity(tableName = "movies")
data class Movie(
    @PrimaryKey
    @Json(name = "id")
    val id: Int, // ID biasanya Int, bukan String (sesuai API TMDB)

    @Json(name = "title")
    val title: String,

    @Json(name = "poster_path")
    val posterPath: String?,

    @Json(name = "overview")
    val overview: String?,

    @Json(name = "vote_average")
    val voteAverage: Double?,

    @Json(name = "release_date")
    val releaseDate: String?,

    @Json(name = "popularity")
    val popularity: Double
) : Parcelable

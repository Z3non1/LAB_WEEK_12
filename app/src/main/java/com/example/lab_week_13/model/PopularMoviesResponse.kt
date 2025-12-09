package com.example.lab_week_13.model

import com.example.lab_week_13.Movie // PENTING: Import dari root package
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PopularMoviesResponse(
    @Json(name = "page") val page: Int,
    @Json(name = "results") val results: List<Movie>,
    @Json(name = "total_pages") val totalPages: Int,
    @Json(name = "total_results") val totalResults: Int
)

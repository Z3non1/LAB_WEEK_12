// 1. Ganti package agar sesuai dengan struktur foldernya (model)
package com.example.lab_week_13.model

// 2. Import class Movie yang benar
// (Asumsi Movie.kt ada di package com.example.lab_week_13 seperti yang kita buat sebelumnya)
import com.example.lab_week_13.Movie

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PopularMoviesResponse(
    @Json(name = "page") val page: Int,
    @Json(name = "results") val results: List<Movie>,
    @Json(name = "total_pages") val totalPages: Int,
    @Json(name = "total_results") val totalResults: Int
)

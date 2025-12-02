package com.example.test_lab_week_13.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val id: Int,

    @Json(name = "title")
    val title: String,

    @Json(name = "poster_path")
    val posterPath: String?,

    @Json(name = "release_date")
    val releaseDate: String?,

    @Json(name = "popularity")
    val popularity: Double,

    @Json(name = "overview")
    val overview: String?
) : Parcelable

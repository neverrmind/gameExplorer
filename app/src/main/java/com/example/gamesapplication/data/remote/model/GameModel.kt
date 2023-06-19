package com.example.gamesapplication.data.remote.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GameModel(
    @Json(name = "name") val name: String? = "",
    @Json(name = "background_image") val background_image: String? = "",
    @Json(name = "metacritic") val metacritic: Int?,
    @Json(name = "genres") val genres: List<GenreModel>? = null
)


@JsonClass(generateAdapter = true)
data class GenreModel(
    @Json(name = "name") val name: String,
)
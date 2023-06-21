package com.example.gamesapplication.data.remote.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GameDetailListModel(
    @Json(name = "id") val id: Int?,
    @Json(name = "name") val name: String? = "",
    @Json(name = "background_image") val background_image: String? = "",
    @Json(name = "description_raw") val description_raw: String? = "",
    @Json(name = "reddit_url") val reddit_url: String? = "",
    @Json(name = "website") val website: String? = "",
)
package com.example.gamesapplication.data.remote.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GameDetailListModel(
    @Json(name = "id") val id: Int?,
)
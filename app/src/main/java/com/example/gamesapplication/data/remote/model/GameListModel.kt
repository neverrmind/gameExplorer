package com.example.gamesapplication.data.remote.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GameListModel(
    @Json(name = "results") val results: List<GameModel>
)

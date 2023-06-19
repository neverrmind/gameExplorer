package com.example.gamesapplication.data.remote

import com.example.gamesapplication.data.remote.model.GameListModel
import com.example.gamesapplication.util.Constants.Companion.GAMES_ENDPOINT
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GameApi {
    @GET(GAMES_ENDPOINT)
    suspend fun getGames(@Query("key") apiKey: String): Response<GameListModel>
}
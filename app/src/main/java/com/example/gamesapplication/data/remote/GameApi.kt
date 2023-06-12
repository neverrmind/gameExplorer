package com.example.gamesapplication.data.remote

import com.example.gamesapplication.data.remote.model.GameModel
import com.example.gamesapplication.util.Constants.Companion.GAMES_ENDPOINT
import retrofit2.Response
import retrofit2.http.GET

interface GameApi {
    @GET(GAMES_ENDPOINT)
    suspend fun getGames(): Response<List<GameModel>>
}
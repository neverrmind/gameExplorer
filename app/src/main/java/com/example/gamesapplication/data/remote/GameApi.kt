package com.example.gamesapplication.data.remote

import com.example.gamesapplication.data.remote.model.GameDetailListModel
import com.example.gamesapplication.data.remote.model.GameListModel
import com.example.gamesapplication.util.Constants.Companion.GAMES_ENDPOINT
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GameApi {
    @GET(GAMES_ENDPOINT)
    suspend fun getGames(
        @Query("page_size") pageSize: Int,
        @Query("page") page: Int,
        @Query("key") apiKey: String
    ): Response<GameListModel>

    @GET("$GAMES_ENDPOINT/{gameId}")
    suspend fun getGameDetails(
        @Path("gameId") gameId: String,
        @Query("key") apiKey: String
    ): Response<GameDetailListModel>

    @GET(GAMES_ENDPOINT)
    suspend fun getGames(
        @Query("search") query: String,
        @Query("page_size") pageSize: Int,
        @Query("page") page: Int,
        @Query("key") apiKey: String
    ): Response<GameListModel>

}
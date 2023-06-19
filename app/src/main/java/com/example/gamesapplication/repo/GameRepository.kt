package com.example.gamesapplication.repo

import com.example.gamesapplication.data.remote.GameApi
import com.example.gamesapplication.data.remote.model.GameModel
import javax.inject.Inject


class GameRepository @Inject constructor(private val gameApi: GameApi) {

    suspend fun getGames(apiKey: String): List<GameModel>? {
        return gameApi.getGames(apiKey).body()?.results
    }
}
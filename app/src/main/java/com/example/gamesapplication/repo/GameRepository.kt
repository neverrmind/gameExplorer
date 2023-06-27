package com.example.gamesapplication.repo

import com.example.gamesapplication.data.remote.GameApi
import com.example.gamesapplication.data.remote.model.GameDetailListModel
import com.example.gamesapplication.data.remote.model.GameModel
import javax.inject.Inject


class GameRepository @Inject constructor(private val gameApi: GameApi) {

    suspend fun getGames(pageSize: Int, page: Int, apiKey: String): List<GameModel>? {
        return gameApi.getGames(pageSize, page, apiKey).body()?.results
    }

    suspend fun getGameDetail(gameId: String, apiKey: String): GameDetailListModel? {
        return gameApi.getGameDetails(gameId, apiKey).body()
    }
}
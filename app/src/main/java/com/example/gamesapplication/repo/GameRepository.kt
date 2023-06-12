package com.example.gamesapplication.repo

import com.example.gamesapplication.data.remote.GameService
import com.example.gamesapplication.domain.item.GameItem
import com.example.gamesapplication.domain.item.toGameItem
import javax.inject.Inject


class GameRepository @Inject constructor(private val gameService: GameService) {

    suspend fun getGames(): List<GameItem> {
        return gameService.getGames().map {
            it.toGameItem()
        }
    }

}
package com.example.gamesapplication.domain

import com.example.gamesapplication.data.remote.model.GameModel
import com.example.gamesapplication.repo.GameRepository
import com.example.gamesapplication.util.Constants
import javax.inject.Inject

class GetGamesUseCase @Inject constructor(private val gameRepository: GameRepository) {
    suspend operator fun invoke(): List<GameModel>? {
        val apiKey = Constants.API_KEY
        return gameRepository.getGames(apiKey)
    }
}
package com.example.gamesapplication.domain

import com.example.gamesapplication.domain.item.GameItem
import com.example.gamesapplication.repo.GameRepository
import javax.inject.Inject

class GetGamesUseCase @Inject constructor(private val gameRepository: GameRepository) {
    suspend operator fun invoke(): List<GameItem> {
        return gameRepository.getGames().shuffled()
    }

}
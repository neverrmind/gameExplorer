package com.example.gamesapplication.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gamesapplication.data.remote.model.GameModel
import com.example.gamesapplication.domain.GetGamesUseCase
import com.example.gamesapplication.repo.GameRepository
import com.example.gamesapplication.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getGamesUseCase: GetGamesUseCase,
    private val gameRepository: GameRepository
) :

    ViewModel() {

    private val _games = MutableStateFlow(emptyList<GameModel>())
    val games: StateFlow<List<GameModel>> get() = _games

    init {
        getGames()
    }

    private fun getGames() {
        viewModelScope.launch {

            val games = gameRepository.getGames(Constants.API_KEY)
            if (games != null) {
                _games.value = games
            }
        }
    }
}
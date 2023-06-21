package com.example.gamesapplication.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gamesapplication.data.remote.model.GameDetailListModel
import com.example.gamesapplication.repo.GameRepository
import com.example.gamesapplication.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val gameRepository: GameRepository
) :
    ViewModel() {
    private val _gameId: MutableStateFlow<String?> = MutableStateFlow(null)
    val gameId: StateFlow<String?> get() = _gameId
    private val _gameDetailModel: MutableStateFlow<GameDetailListModel?> = MutableStateFlow(null)
    val gameDetailModel: StateFlow<GameDetailListModel?> get() = _gameDetailModel

    fun getGameDetail(gameId: String?) {
        viewModelScope.launch {

            val gameDetail = gameId?.let { gameRepository.getGameDetail(it, Constants.API_KEY) }
            if (gameDetail != null) {
                _gameDetailModel.value = gameDetail
            }
        }
    }
}
package com.example.gamesapplication.ui.detail

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gamesapplication.data.remote.GameInfoStorage
import com.example.gamesapplication.data.remote.model.GameDetailListModel
import com.example.gamesapplication.data.remote.model.GameModel
import com.example.gamesapplication.repo.GameRepository
import com.example.gamesapplication.util.Constants
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val gameRepository: GameRepository,
    private val preferences: SharedPreferences,
    private val moshi: Moshi,
) :
    ViewModel() {
    private val _gameId: MutableStateFlow<String?> = MutableStateFlow(null)
    val gameId: StateFlow<String?> get() = _gameId

    private val _gameDetailModel: MutableStateFlow<GameDetailListModel?> = MutableStateFlow(null)
    val gameDetailModel: StateFlow<GameDetailListModel?> get() = _gameDetailModel

    private val _isFavorite: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isFavorite: StateFlow<Boolean> get() = _isFavorite

    private val _gameModel: MutableStateFlow<GameModel?> = MutableStateFlow(null)

    private val _favorite: MutableStateFlow<List<GameModel>> = MutableStateFlow(emptyList())

    init {
        _gameModel.value = GameInfoStorage.gameModel
        _isFavorite.value = checkFavorite()
    }

    fun getGameDetail(gameId: String?) {
        viewModelScope.launch {

            val gameDetail = gameId?.let { gameRepository.getGameDetail(it, Constants.API_KEY) }
            if (gameDetail != null) {
                _gameDetailModel.value = gameDetail
                _gameId.value = gameId
            }
        }
    }

    private fun getFavorites(): ArrayList<GameModel>? {
        val type = Types.newParameterizedType(List::class.java, GameModel::class.java)
        preferences.getString("favorites", null)?.let {
            return moshi.adapter<ArrayList<GameModel>>(type).fromJson(it)
        }
        return arrayListOf()
    }

    fun addFavorites() {
        val list = getFavorites()
        _gameModel.value?.let { list?.add(it) }

        val type = Types.newParameterizedType(List::class.java, GameModel::class.java)
        val json = moshi.adapter<List<GameModel?>>(type).toJson(list)
        preferences.edit().putString("favorites", json)
            .apply()
        _isFavorite.value = true
    }

    fun deleteFavorites() {
        val list = getFavorites()
        val gameModel = _gameModel.value

        if (gameModel != null) {
            list?.remove(gameModel)

            val type = Types.newParameterizedType(List::class.java, GameModel::class.java)
            val json = moshi.adapter<List<GameModel?>>(type).toJson(list)
            preferences.edit().putString("favorites", json).apply()
        }
        _isFavorite.value = false
    }

    fun checkFavorite(): Boolean {
        val favoritesList = getFavorites()
        val gameId = _gameId.value?.toIntOrNull()
        var isFavorites: Boolean = false
        isFavorites = !favoritesList?.filter { it.id == gameId }.isNullOrEmpty()
        return isFavorites
    }
}
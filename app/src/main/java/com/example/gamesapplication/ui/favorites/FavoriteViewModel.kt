package com.example.gamesapplication.ui.favorites

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import com.example.gamesapplication.data.remote.model.GameModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val preferences: SharedPreferences,
    private val moshi: Moshi
) : ViewModel() {
    private val _games: MutableStateFlow<ArrayList<GameModel>?> = MutableStateFlow(arrayListOf())
    val games: StateFlow<ArrayList<GameModel>?> get() = _games

    init {
        _games.value = getFavorites()
    }

    private fun getFavorites(): ArrayList<GameModel>? {
        val type = Types.newParameterizedType(List::class.java, GameModel::class.java)
        preferences.getString("favorites", null)?.let {
            return moshi.adapter<ArrayList<GameModel>>(type).fromJson(it)
        }
        return arrayListOf()
    }

    fun deleteFavorites(game: GameModel) {
        val list = getFavorites()
        list?.remove(game)

        val type = Types.newParameterizedType(List::class.java, GameModel::class.java)
        val json = moshi.adapter<List<GameModel?>>(type).toJson(list)
        preferences.edit().putString("favorites", json).apply()

        _games.value = getFavorites() // Favori oyunları güncelle
    }
}
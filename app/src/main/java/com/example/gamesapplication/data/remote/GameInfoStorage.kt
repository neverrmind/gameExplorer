package com.example.gamesapplication.data.remote

import com.example.gamesapplication.data.remote.model.GameModel

object GameInfoStorage {
    var gameModel: GameModel? = null

    fun clearStorage() {
        gameModel = null
    }
}
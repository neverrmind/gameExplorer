package com.example.gamesapplication.ui.detail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun DetailScreen(name: String?) {
    val detailViewModel: DetailViewModel = hiltViewModel()
    LaunchedEffect(detailViewModel.gameId.collectAsState().value) {
        detailViewModel.getGameDetail(name)
    }
}
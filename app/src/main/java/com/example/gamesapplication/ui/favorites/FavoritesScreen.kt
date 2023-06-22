package com.example.gamesapplication.ui.favorites

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import com.example.gamesapplication.R
import com.example.gamesapplication.navigation.Screen

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(navController: NavController) {

    val isClickedGames = remember { mutableStateOf(false) }
    val isClickedFavorites = remember { mutableStateOf(true) }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    titleContentColor = Color(0xFFFFFFFF),
                    containerColor = Color(0xFF1064BC)
                ),
                title = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        text = "Favorites"
                    )
                }
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = Color(0xFFFFFFFF)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Tab(
                        selected = true,
                        onClick = {
                            isClickedGames.value = true;
                            isClickedFavorites.value = false;
                            navController.navigate(Screen.HomeScreen.route)
                        },
                        modifier = Modifier.weight(1f),
                        icon = {
                            Icon(
                                painter = painterResource(R.drawable.menu_games),
                                contentDescription = null,
                                tint = if (isClickedGames.value) Color.Blue else Color.Gray
                            )
                        }
                    )
                    Tab(
                        selected = false,
                        onClick = {
                            isClickedFavorites.value = true;
                            isClickedGames.value = false;
                            navController.navigate(Screen.FavoritesScreen.route)
                        },
                        modifier = Modifier.weight(1f),
                        icon = {
                            Icon(
                                painter = painterResource(R.drawable.menu___home),
                                contentDescription = null,
                                tint = if (isClickedFavorites.value) Color.Blue else Color.Gray
                            )
                        }
                    )
                }
            }
        }
    ) {
        Column {
            Text("test")
        }
    }

}




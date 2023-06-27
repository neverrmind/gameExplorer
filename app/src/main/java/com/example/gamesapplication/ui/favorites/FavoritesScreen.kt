package com.example.gamesapplication.ui.favorites

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.gamesapplication.R
import com.example.gamesapplication.data.remote.model.GameModel
import com.example.gamesapplication.navigation.Screen

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(navController: NavController) {
    val favoriteViewModel: FavoriteViewModel = hiltViewModel()
    val isClickedGames = remember { mutableStateOf(false) }
    val isClickedFavorites = remember { mutableStateOf(true) }
    val games = favoriteViewModel.games.collectAsState()

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
                        text = stringResource(R.string.favorites)
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
            LazyColumn(
                modifier = Modifier.padding(top = 56.dp, bottom = 70.dp)
            ) {
                items(games.value?.toList() ?: emptyList()) { game: GameModel ->
                    GameCard(
                        game = game,
                        navController,
                        favoriteViewModel,
                        onDeleteClicked = { deletedGame ->
                            favoriteViewModel.deleteFavorites(deletedGame)
                        })
                }
                if (games.value.isNullOrEmpty()) {
                    item {
                        Box(
                            modifier = Modifier.padding(top = 243.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                modifier = Modifier.fillMaxWidth().padding(16.dp),
                                textAlign = TextAlign.Center,
                                text = stringResource(R.string.no_favorites_found),
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun GameCard(
    game: GameModel,
    navController: NavController,
    favoriteViewModel: FavoriteViewModel,
    onDeleteClicked: (GameModel) -> Unit
) {
    val image = rememberImagePainter(data = game.background_image)

    Card(
        elevation = CardDefaults.cardElevation(),
        shape = RoundedCornerShape(5.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(136.dp),
        colors = CardDefaults.cardColors(Color.White)
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Image(
                painter = image,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(120.dp)
                    .fillMaxHeight()
                    .clip(shape = RoundedCornerShape(0.dp))
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 16.dp),
                verticalArrangement = Arrangement.SpaceBetween,
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = game.name.orEmpty(),
                        fontSize = 20.sp,
                        letterSpacing = 0.38.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Icon(
                        modifier = Modifier.clickable {
                            onDeleteClicked(game)
                        },
                        painter = painterResource(R.drawable.icon_garbage),
                        contentDescription = null,
                    )
                }

                Column(
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Row() {
                        Text(
                            text = stringResource(R.string.metacritic),
                            modifier = Modifier,
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp,
                            lineHeight = 25.sp,
                            textAlign = TextAlign.Left,
                            letterSpacing = 0.38.sp,
                        )
                        Text(
                            text = game.metacritic.toString(),
                            modifier = Modifier,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            lineHeight = 25.sp,
                            letterSpacing = 0.38.sp,
                            color = Color(0xFFD80000)
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "${game.genres?.joinToString { it.name }}",
                        modifier = Modifier,
                        fontWeight = FontWeight.Light,
                        fontSize = 13.sp,
                        lineHeight = 18.sp,
                        letterSpacing = (-0.08).sp,
                        textAlign = TextAlign.Left,
                        color = Color(0xFF8A8A8F)
                    )
                }
            }
        }
    }
}
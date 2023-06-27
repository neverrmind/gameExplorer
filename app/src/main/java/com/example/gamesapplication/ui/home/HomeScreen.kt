package com.example.gamesapplication.ui.home

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
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
fun HomeScreen(navController: NavController) {
    val homeViewModel: HomeViewModel = hiltViewModel()
    val games by homeViewModel.games.collectAsState()
    val loading by homeViewModel.loading.collectAsState()
    val isClickedGames = remember { mutableStateOf(true) }
    val isClickedFavorites = remember { mutableStateOf(false) }
    val lazyListState = rememberLazyListState()
    val searchQuery by homeViewModel.searchQuery.collectAsState()
    val searchResult by homeViewModel.searchResult.collectAsState()

    val endReached = remember {
        derivedStateOf {
            val visibleItemCount = lazyListState.layoutInfo.visibleItemsInfo.size
            val lastVisibleItemIndex =
                lazyListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: -1
            val totalItemCount = lazyListState.layoutInfo.totalItemsCount
            visibleItemCount > 0 && lastVisibleItemIndex >= totalItemCount - 1
        }
    }
    LaunchedEffect(endReached.value) {
        if (endReached.value) {
            homeViewModel.loadNextPage()
        }
    }

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
                        text = stringResource(R.string.games)
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
        Column(
            modifier = Modifier
                .background(Color.LightGray)
                .navigationBarsPadding()
                .padding(top = 65.dp, bottom = 100.dp),
        ) {
            TextField(
                value = searchQuery,
                onValueChange = { homeViewModel.setSearchQuery(it) },
                label = { Text(text = stringResource(R.string.search_for_games)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.White),
                leadingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.icon_search),
                        contentDescription = null
                    )
                }
            )
            LazyColumn(
                state = lazyListState,
            ) {
                items(if (searchQuery.isNotEmpty()) searchResult else games) { game: GameModel ->
                    GameCard(game = game, navController, homeViewModel)
                }

            }
            if (loading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    strokeWidth = 4.dp
                )
            }
        }
    }
}

@Composable
fun GameCard(game: GameModel, navController: NavController, homeViewModel: HomeViewModel) {
    val image = rememberImagePainter(data = game.background_image)

    Card(
        elevation = CardDefaults.cardElevation(),
        shape = RoundedCornerShape(5.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(136.dp)
            .clickable {
                homeViewModel.setGameInfo(game)
                navController.navigate(Screen.DetailScreen.withArgs(game.id.toString()))
            },
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
                Text(
                    text = game.name.orEmpty(),
                    fontSize = 20.sp,
                    letterSpacing = 0.38.sp,
                    fontWeight = FontWeight.Bold
                )
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
package com.example.gamesapplication.ui.home


import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.gamesapplication.data.remote.model.GameModel
import com.example.gamesapplication.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    val homeViewModel: HomeViewModel = hiltViewModel()
    val games by homeViewModel.games.collectAsState()

    Column {
        TopAppBar(
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                titleContentColor = Color(0xFFFFFFFF),
                containerColor = Color(0xFF1064BC)
            ),
            title = {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = "Games"
                )
            }
        )
        LazyColumn {
            items(games) { game: GameModel ->
                GameCard(game = game, navController)

            }
        }
    }
}

@Composable
fun GameCard(game: GameModel, navController: NavController) {
    val image = rememberImagePainter(data = game.background_image)

    Card(
        elevation = CardDefaults.cardElevation(),
        shape = RoundedCornerShape(5.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(136.dp)
            .clickable {
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
                            text = "metacritic: ",
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
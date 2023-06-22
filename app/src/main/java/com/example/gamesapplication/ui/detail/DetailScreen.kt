package com.example.gamesapplication.ui.detail

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.gamesapplication.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(navController: NavController, name: String?) {
    val detailViewModel: DetailViewModel = hiltViewModel()
    LaunchedEffect(detailViewModel.gameId.collectAsState().value) {
        detailViewModel.getGameDetail(name)
    }
    val gameDetail by detailViewModel.gameDetailModel.collectAsState()
    val image = rememberImagePainter(data = gameDetail?.background_image)
    val name = gameDetail?.name
    val description = gameDetail?.description_raw
    val redditUrl = gameDetail?.reddit_url
    val website = gameDetail?.website
    val isClicked = remember { mutableStateOf(false) }
    val isClickedWeb = remember { mutableStateOf(false) }
    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { }
    Column {
        TopAppBar(
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                titleContentColor = Color(0xFFFFFFFF),
                containerColor = Color(0xFF1064BC)
            ),
            navigationIcon = {
                Icon(
                    painter = painterResource(R.drawable.chevron_left),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.clickable {
                        navController.popBackStack()
                    }
                )
            },
            actions = {
                Icon(
                    painter = painterResource(R.drawable.heart_favorite_save___negative),
                    contentDescription = null,
                    tint = Color.White
                )
            },
            title = {
                if (name != null) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        text = name
                    )
                }
            }
        )
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())
        ) {
            Box {
                Image(
                    painter = image,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxWidth().height(275.dp)
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)

                ) {
                    if (name != null) {
                        Text(
                            text = name,
                            modifier = Modifier
                                .padding(5.dp),
                            fontWeight = FontWeight.Bold,
                            fontSize = 36.sp,
                            letterSpacing = 0.38.sp,
                            textAlign = TextAlign.Right,
                            color = Color(0xFFFFFFFF),
                        )
                    }
                }
            }
            Box(
                modifier = Modifier.padding(16.dp)
            ) {
                Column {
                    Text(
                        text = stringResource(R.string.game_description),
                        modifier = Modifier
                            .padding(5.dp),
                        fontWeight = FontWeight.Medium,
                        fontSize = 17.sp,
                        letterSpacing = 0.41.sp,
                        textAlign = TextAlign.Right,
                    )
                    if (description != null) {
                        Text(
                            text = description,
                            modifier = Modifier
                                .padding(8.dp),
                            fontWeight = FontWeight.Normal,
                            fontSize = 10.sp,
                            letterSpacing = 0.41.sp,
                            textAlign = TextAlign.Left,
                            maxLines = 4,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
            Divider(
                color = Color.LightGray,
                thickness = 1.dp,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Box(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    modifier = Modifier
                        .clickable {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(redditUrl))
                            launcher.launch(intent)
                            isClicked.value = true
                        },
                    text = stringResource(R.string.visit_reddit),
                    fontWeight = FontWeight.Normal,
                    fontSize = 17.sp,
                    letterSpacing = 0.41.sp,
                    textAlign = TextAlign.Right,
                    color = if (isClicked.value) Color.Blue else Color.Black,
                )
            }
            Divider(
                color = Color.LightGray,
                thickness = 1.dp,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Box(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    modifier = Modifier
                        .clickable {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(website))
                            launcher.launch(intent)
                            isClickedWeb.value = true
                        },
                    text = stringResource(R.string.visit_website),
                    fontWeight = FontWeight.Normal,
                    fontSize = 17.sp,
                    letterSpacing = 0.41.sp,
                    textAlign = TextAlign.Right,
                    color = if (isClickedWeb.value) Color.Blue else Color.Black,

                    )
            }
            Divider(
                color = Color.LightGray,
                thickness = 1.dp,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    }
}
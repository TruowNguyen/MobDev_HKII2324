package com.example.sumsmart.ui

import GameScreen4ViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.sumsmart.R
import com.example.sumsmart.data.NumberData
import com.example.sumsmart.navigation.NavGraphItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameScreen4(
    navController: NavHostController,
    gameScreen4ViewModel: GameScreen4ViewModel = viewModel()
) {
    val numbersGrid by gameScreen4ViewModel.numbersGrid.collectAsState()
    val targetSum by gameScreen4ViewModel.targetSum.collectAsState()
    val selectedNumbers by gameScreen4ViewModel.selectedNumbers.collectAsState()
    val resultMessage by gameScreen4ViewModel.resultMessage.collectAsState()
    val score by gameScreen4ViewModel.score.collectAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.game4_title),
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    navController.currentBackStackEntryAsState().value?.destination?.route?.let { currentRoute ->
                        if (currentRoute != NavGraphItem.Home.route) {
                            IconButton(onClick = { navController.navigateUp() }) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = stringResource(id = R.string.back_button),
                                    tint = Color.Black
                                )
                            }
                        }
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background)
        ) {
            Image(
                painter = painterResource(id = R.drawable.background_4),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.Center)
            )
            Column(
                modifier = Modifier
                    .padding(16.dp),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(32.dp))

                LazyVerticalGrid(
                    columns = GridCells.Fixed(6),
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                ) {
                    items(numbersGrid.size) { index ->
                        NumberCard4(
                            numberData = numbersGrid[index],
                            isSelected = selectedNumbers.contains(index),
                            onClick = { gameScreen4ViewModel.selectNumber(index) }
                        )
                    }
                }
                Text(
                    text = "Nối 2 số sao cho tổng bằng $targetSum",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.padding(16.dp)
                )
                Text(
                    text = "Điểm số: $score",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                resultMessage?.let {
                    Text(
                        text = it,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (it.startsWith("Đúng rồi")) Color.Green else Color.Red,
                        modifier = Modifier
                    )
                }

                Button(onClick = { gameScreen4ViewModel.resetGame() }) {
                    Text(text = "Chơi lại")
                }
            }
        }
    }
}

@Composable
fun NumberCard4(
    numberData: NumberData,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    if (numberData.drawableId != 0) { // Only display numbers that are not removed
        Card(
            modifier = Modifier
                .size(64.dp)
                .padding(4.dp)
                .clickable { onClick() },
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(containerColor = if (isSelected) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.primary),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Image(
                    painter = painterResource(id = numberData.drawableId),
                    contentDescription = null,
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    } else {
        Box(
            modifier = Modifier
                .size(64.dp)
                .padding(4.dp)
        )
    }
}


@Preview(showBackground = true)
@Composable
fun GameScreen4Preview() {
    val navController = rememberNavController()
    GameScreen4(navController = navController)
}

package com.example.sumsmart.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.navigation.compose.rememberNavController
import com.example.sumsmart.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameScreen2(
    navController: NavHostController,
    gameScreen2ViewModel: GameScreen2ViewModel = viewModel()
) {
    val numbersTop by gameScreen2ViewModel.numbersTop.collectAsState()
    val numbersBottom by gameScreen2ViewModel.numbersBottom.collectAsState()
    val targetSum by gameScreen2ViewModel.targetSum.collectAsState()
    val selectedNumberTop by gameScreen2ViewModel.selectedNumberTop.collectAsState()
    val selectedNumberBottom by gameScreen2ViewModel.selectedNumberBottom.collectAsState()
    val resultMessage by gameScreen2ViewModel.resultMessage.collectAsState()
    val score by gameScreen2ViewModel.score.collectAsState()
    val selectedNumbersTopDisplay by gameScreen2ViewModel.selectedNumbersTopDisplay.collectAsState()
    val selectedNumbersBottomDisplay by gameScreen2ViewModel.selectedNumbersBottomDisplay.collectAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.game2_title),
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
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
        ) {
            Image(
                painter = painterResource(id = R.drawable.background_2),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.Center)
                    .background(Color.Transparent)
            )
            Column(
                modifier = Modifier
                    .padding(8.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Người chơi 1: Chọn số từ dãy trên",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.tertiary,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                NumberGrid(numbers = numbersTop, selectedNumbers = selectedNumberTop, onNumberClick = gameScreen2ViewModel::selectNumberTop)

                Spacer(modifier = Modifier.height(20.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    selectedNumbersTopDisplay.forEach { resourceId ->
                        Image(
                            painter = painterResource(id = resourceId),
                            contentDescription = null,
                            modifier = Modifier
                                .size(48.dp)
                                .padding(4.dp)
                        )
                    }
                    selectedNumbersBottomDisplay.forEach { resourceId ->
                        Image(
                            painter = painterResource(id = resourceId),
                            contentDescription = null,
                            modifier = Modifier
                                .size(48.dp)
                                .padding(4.dp)
                        )
                    }
                }

                Text(
                    text = "Tổng mục tiêu: $targetSum",
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
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    LaunchedEffect(it) {
                        kotlinx.coroutines.delay(1000)
                        gameScreen2ViewModel.clearMessage()

                    }
                }

                Text(
                    text = "Điểm: $score",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                ControlButtons(navController, gameScreen2ViewModel)

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Người chơi 2: Chọn số từ dãy dưới",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.tertiary,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                NumberGrid(numbers = numbersBottom, selectedNumbers = selectedNumberBottom, onNumberClick = gameScreen2ViewModel::selectNumberBottom)
            }
        }
    }
}

@Composable
fun NumberGrid(
    numbers: List<Int>,
    selectedNumbers: List<Int>,
    onNumberClick: (Int) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(5),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        items(numbers) { number ->
            NumberCard2(
                number = number,
                isSelected = selectedNumbers.contains(number),
                onClick = { onNumberClick(number) }
            )
        }
    }
}

@Composable
fun NumberCard2(
    number: Int,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Log.d("NumberCard", "Number: $number")
    Card(
        modifier = Modifier
            .size(80.dp)
            .padding(4.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize().background(Color.Transparent)
        ) {
            Image(
                painter = painterResource(id = number),
                contentDescription = null,
                modifier = Modifier
                    .size(58.dp)
                    .align(Alignment.Center)
            )
        }
    }
}

@Composable
fun ControlButtons(
    navController: NavHostController,
    viewModel: GameScreen2ViewModel
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Button(
            onClick = { viewModel.checkSum() },
            shape = RoundedCornerShape(50),
            modifier = Modifier.padding(8.dp)
        ) {
            Text(text = "Tính nào!")
        }

        Button(
            onClick = { navController.navigateUp() },
            modifier = Modifier.padding(8.dp),
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
        ) {
            Text(text = "Quay lại")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GameScreen2Preview() {
    val navController = rememberNavController()
    GameScreen2(navController = navController)
}


package com.example.sumsmart.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
fun GameScreen1(
    navController: NavHostController,
    gameScreen1ViewModel: GameScreen1ViewModel = viewModel()
) {
    val numbers by gameScreen1ViewModel.numbers.collectAsState()
    val targetSum by gameScreen1ViewModel.targetSum.collectAsState()
    val selectedNumbers by gameScreen1ViewModel.selectedNumbers.collectAsState()
    val resultMessage by gameScreen1ViewModel.resultMessage.collectAsState()
    val selectedNumbersDisplay by gameScreen1ViewModel.selectedNumbersDisplay.collectAsState()
    val score by gameScreen1ViewModel.score.collectAsState() // Add this line

    Log.d("GameScreen1", "Numbers: $numbers")
    Log.d("GameScreen1", "Target Sum: $targetSum")
    Log.d("GameScreen1", "Selected Numbers: $selectedNumbers")
    Log.d("GameScreen1", "Result Message: $resultMessage")

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.game1_title),
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
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Hãy chọn số để tính:",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    selectedNumbersDisplay.forEach { resourceId ->
                        Image(
                            painter = painterResource(id = resourceId),
                            contentDescription = null,
                            modifier = Modifier
                                .size(64.dp)
                                .padding(4.dp)
                        )
                    }
                }
                NumberDisplay(numbers, selectedNumbers, gameScreen1ViewModel)

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Tổng cần tính: $targetSum",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.padding(bottom = 16.dp)
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
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                }

                ControlButtons(navController, gameScreen1ViewModel)
            }
        }
    }
}

@Composable
fun NumberDisplay(
    numbers: List<Int>,
    selectedNumbers: List<Int>,
    gameScreen1ViewModel: GameScreen1ViewModel
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        contentPadding = PaddingValues(8.dp),
        modifier = Modifier.padding(8.dp)
    ) {
        items(numbers) { number ->
            NumberCard(
                number = number,
                isSelected = selectedNumbers.contains(number),
                onClick = { gameScreen1ViewModel.selectNumber(number) }
            )
        }
    }
}

@Composable
fun NumberCard(
    number: Int,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Log.d("NumberCard", "Number: $number")
    Card(
        modifier = Modifier
            .size(80.dp)
            .padding(4.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        elevation = CardDefaults.cardElevation(0.dp),
        onClick = onClick
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.background(Color.Transparent).fillMaxSize(),
        ) {
            Image(
                painter = painterResource(id = number),
                contentDescription = null,
                modifier = Modifier
                    .size(64.dp)
                    .align(Alignment.Center)
            )
        }
    }
}

@Composable
fun ControlButtons(
    navController: NavHostController,
    gameScreen1ViewModel: GameScreen1ViewModel,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Button(
            onClick = { gameScreen1ViewModel.checkSum() },
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Text(text = "Tính nào!", color = Color.White)
        }

        Button(
            onClick = {
                navController.popBackStack()  // Navigate back
            },
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
        ) {
            Text(text = "Quay lại", color = Color.White)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GameScreen1Preview() {
    val navController = rememberNavController()
    GameScreen1(navController = navController)
}

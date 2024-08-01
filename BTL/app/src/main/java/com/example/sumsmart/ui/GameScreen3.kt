package com.example.sumsmart.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.sumsmart.R
import com.example.sumsmart.navigation.NavGraphItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameScreen3(
    navController: NavHostController,
    gameScreen3ViewModel: GameScreen3ViewModel = viewModel()
) {
    val player1Input by gameScreen3ViewModel.player1Input.collectAsState()
    val player2Input by gameScreen3ViewModel.player2Input.collectAsState()
    val resultMessage by gameScreen3ViewModel.resultMessage.collectAsState()
    val targetSum by gameScreen3ViewModel.targetSum.collectAsState()
    val score by gameScreen3ViewModel.score.collectAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.game3_title),
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
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Người chơi 1: Nhập số vào ô dưới",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                NumberField(
                    label = R.string.x,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    value = player1Input,
                    onValueChanged = gameScreen3ViewModel::onPlayer1InputChanged
                )
                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Người chơi 2: Nhập số vào ô dưới",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                NumberField(
                    label = R.string.y,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    value = player2Input,
                    onValueChanged = gameScreen3ViewModel::onPlayer2InputChanged
                )
                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Tổng mục tiêu: $targetSum",
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

                resultMessage.let {
                    Text(
                        text = it,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (it.startsWith("Đúng rồi")) Color.Green else Color.Red,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                }

                Button(onClick = {
                    gameScreen3ViewModel.checkSum()
                }) {
                    Text("Kiểm tra tổng")
                }
            }
        }
    }
}

@Composable
fun NumberField(
    @StringRes label: Int,
    keyboardOptions: KeyboardOptions,
    value: String,
    onValueChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = value,
        onValueChange = onValueChanged,
        modifier = modifier,
        singleLine = true,
        label = { Text(stringResource(label)) },
        keyboardOptions = keyboardOptions
    )
}

@Preview(showBackground = true)
@Composable
fun GameScreen3Preview() {
    val navController = rememberNavController()
    GameScreen3(navController = navController)
}

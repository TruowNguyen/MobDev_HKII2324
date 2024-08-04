package com.example.sumsmart.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
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
import androidx.compose.ui.text.TextStyle
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
fun GameScreen5(
    navController: NavHostController,
    gameScreen5ViewModel: GameScreen5ViewModel = viewModel()
) {
    val player1Input by gameScreen5ViewModel.player1Input.collectAsState()
    val player2Input by gameScreen5ViewModel.player2Input.collectAsState()
    val equation by gameScreen5ViewModel.equation.collectAsState()
    val resultMessage by gameScreen5ViewModel.resultMessage.collectAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Thử tài đoán tổng",
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
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = equation,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(32.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextField(
                        value = player1Input,
                        onValueChange = { gameScreen5ViewModel.updatePlayer1Input(it) },
                        textStyle = TextStyle(fontSize = 24.sp, color = Color.Black),
                        modifier = Modifier
                            .background(Color.White, shape = MaterialTheme.shapes.medium)
                            .padding(16.dp)
                            .width(100.dp),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done
                        )
                    )
                    Text(
                        text = "=",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                    TextField(
                        value = player2Input,
                        onValueChange = { gameScreen5ViewModel.updatePlayer2Input(it) },
                        textStyle = TextStyle(fontSize = 24.sp, color = Color.Black),
                        modifier = Modifier
                            .background(Color.White, shape = MaterialTheme.shapes.medium)
                            .padding(16.dp)
                            .width(100.dp),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done
                        )
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                resultMessage?.let {
                    Text(
                        text = it,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (it.startsWith("Đúng rồi")) Color.Green else Color.Red,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                    LaunchedEffect(it) {
                        // Delay for 3 seconds to clear the message
                        kotlinx.coroutines.delay(3000)
                        gameScreen5ViewModel.clearMessage()
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                Button(onClick = { gameScreen5ViewModel.checkSum() }) {
                    Text(text = "Kiểm tra")
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = { gameScreen5ViewModel.resetGame() }) {
                    Text(text = "Chơi lại")
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GameScreen5Preview() {
    val navController = rememberNavController()
    GameScreen5(navController = navController)
}

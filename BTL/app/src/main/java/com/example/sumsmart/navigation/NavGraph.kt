package com.example.sumsmart.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.sumsmart.ui.GameScreen1
import com.example.sumsmart.ui.GameScreen2
import com.example.sumsmart.ui.GameScreen3
import com.example.sumsmart.ui.GameScreen4
import com.example.sumsmart.ui.MainScreen

sealed class NavGraphItem(val route: String, val titleRes: Int) {
    object Home : NavGraphItem("main_screen", com.example.sumsmart.R.string.app_name)
    object Game1 : NavGraphItem("game1", com.example.sumsmart.R.string.game1_title)
    object Game2 : NavGraphItem("game2", com.example.sumsmart.R.string.game2_title)
    object Game3 : NavGraphItem("game3", com.example.sumsmart.R.string.game3_title)
    object Game4 : NavGraphItem("game4", com.example.sumsmart.R.string.game4_title)
}

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = NavGraphItem.Home.route) {
        composable(NavGraphItem.Home.route) { MainScreen(navController = navController) }
        composable(NavGraphItem.Game1.route) { GameScreen1(navController = navController)}
        composable(NavGraphItem.Game2.route) { GameScreen2(navController = navController) }
        composable(NavGraphItem.Game3.route) { GameScreen3(navController = navController)  }
        composable(NavGraphItem.Game4.route) { GameScreen4(navController = navController) }
    }
}

package com.example.flightsearchapplication.data

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Star
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.flightsearchapplication.R
import com.example.flightsearchapplication.navigation.Screen

sealed class NavigationItem(
    val screen: Screen,
    @StringRes val title: Int,
    val icon: ImageVector
) {
    object Home: NavigationItem(
        screen = Screen.Home,
        title = R.string.navigation_title_home,
        icon = Icons.Outlined.Home
    )

    object Favorite: NavigationItem(
        screen = Screen.Favorities,
        title = R.string.navigation_title_favority,
        icon = Icons.Outlined.Star
    )

    object Airport: NavigationItem(
        screen = Screen.Airport,
        title = R.string.navigation_title_airport,
        icon = Icons.Outlined.ArrowBack
    )
}
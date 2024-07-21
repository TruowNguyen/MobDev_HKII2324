package com.example.flightsearchapplication.navigation

sealed class Screen(
    val route: String
) {
    object Home: Screen(HOME)
    object Favorities: Screen(FAVORITIES)
    object Airport: Screen(AIRPORT)

    private companion object{
        const val HOME = "home"
        const val FAVORITIES = "favorities"
        const val AIRPORT = "airport"
    }

}
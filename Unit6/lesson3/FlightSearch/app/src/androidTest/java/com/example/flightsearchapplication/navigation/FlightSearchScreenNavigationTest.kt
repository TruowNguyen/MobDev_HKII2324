package com.example.flightsearchapplication.navigation

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.example.flightsearchapplication.R
import com.example.flightsearchapplication.data.NavigationItem
import com.example.flightsearchapplication.ui.FlightSearchScreen
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class FlightSearchScreenNavigationTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var navController: TestNavHostController

    @Before
    fun setupCupcakeNavHost() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current).apply {
                navigatorProvider.addNavigator(ComposeNavigator())
            }
            FlightSearchScreen(navHostController = navController)
        }
    }

    @Test
    fun flightSearchNavHost_verifyStartDestination() {
        navController.assertCurrentRouteName(NavigationItem.Home.screen.route)
    }

    @Test
    fun flightSearchNavHost_verifyBackNavigationNotShownOnStartOrderScreen() {
        val backText = composeTestRule.activity.getString(R.string.back_button)
        composeTestRule.onNodeWithContentDescription(backText).assertDoesNotExist()
    }

    @Test
    fun flightSearchNavHost_navigatesToSelectFavoriteScreen() {
        navigateToFavoriteScreen()
    }

    @Test
    fun flightSearchNavHost_navigatesToSelectFavoriteScreenAndReturnBack() {
        navigateToFavoriteScreen()
        performNavigateUp()
        navController.assertCurrentRouteName(NavigationItem.Home.screen.route)
    }

    @Test
    fun navigateToAirportScreen() {
        composeTestRule
            .onNodeWithContentDescription(label = composeTestRule.activity.getString(R.string.search_field_clear_button))
            .performClick()
        composeTestRule
            .onNodeWithText(text = composeTestRule.activity.getString(R.string.search_field_label))
            .performTextInput("opo")
        composeTestRule.onAllNodesWithTag(testTag = composeTestRule.activity.getString(R.string.airport_tag))[0]
            .performClick()
        navController.assertCurrentRouteName(NavigationItem.Airport.screen.route)
    }

    private fun performNavigateUp() {
        val backText = composeTestRule.activity.getString(R.string.back_button)
        composeTestRule.onNodeWithContentDescription(backText).performClick()
    }

    private fun navigateToFavoriteScreen() {
        composeTestRule.onNodeWithStringId(R.string.navigation_title_favority)
            .performClick()
        navController.assertCurrentRouteName(NavigationItem.Favorite.screen.route)
    }

}
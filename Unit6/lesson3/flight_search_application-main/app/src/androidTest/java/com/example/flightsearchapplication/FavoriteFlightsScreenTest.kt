package com.example.flightsearchapplication

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.SemanticsProperties.TestTag
import androidx.compose.ui.semantics.getOrNull
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.example.flightsearchapplication.data.NavigationItem
import com.example.flightsearchapplication.navigation.assertCurrentRouteName
import com.example.flightsearchapplication.ui.FlightSearchScreen
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class FavoriteFlightsScreenTest {

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
    fun makeFlightFavoriteTest() {
        composeTestRule
            .onNodeWithContentDescription(label = composeTestRule.activity.getString(R.string.search_field_clear_button))
            .performClick()
        composeTestRule
            .onNodeWithText(text = composeTestRule.activity.getString(R.string.search_field_label))
            .performTextInput("opo")
        composeTestRule.onAllNodesWithTag(testTag = composeTestRule.activity.getString(R.string.airport_tag))[0]
            .performClick()
        navController.assertCurrentRouteName(NavigationItem.Airport.screen.route)

        //
        val favoriteButton = composeTestRule
            .onAllNodesWithContentDescription(
                label = composeTestRule.activity.getString(R.string.favorite_button),
                useUnmergedTree = true
            )[0]
        favoriteButton.assertExists()
        var testTagValue = favoriteButton.fetchSemanticsNode().config.getOrNull(key = TestTag)
        val expectedTestTagValue: String = if (testTagValue == "NonFavorite") {
            "Favorite"
        } else {
            "NonFavorite"
        }
        favoriteButton.performClick()
        testTagValue = favoriteButton.fetchSemanticsNode().config.getOrNull(key = TestTag)
        assertEquals(testTagValue, expectedTestTagValue)
    }
}
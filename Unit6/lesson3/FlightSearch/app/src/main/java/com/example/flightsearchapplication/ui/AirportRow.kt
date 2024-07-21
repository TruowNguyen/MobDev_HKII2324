package com.example.flightsearchapplication.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.flightsearchapplication.R
import com.example.flightsearchapplication.data.Airport
import com.example.flightsearchapplication.data.NavigationItem

@Composable
fun AirportRow(
    airport: Airport,
    highlitedPosition: List<Int>,

    navHostController: NavHostController,
    modifier: Modifier = Modifier,
    currentAirport: MutableState<Airport>
) {

    Row(modifier = modifier
        .clickable {
            currentAirport.value = airport
            navHostController.navigate(route = NavigationItem.Airport.screen.route)
        }
        .testTag(stringResource(id = R.string.airport_tag))
    ) {
        Text(
            text =
            AnnotatedString(
                text = "${airport.iataCode} ${airport.name}",
                spanStyles = listOf(
                    AnnotatedString.Range(
                        SpanStyle(
                            background = MaterialTheme.colorScheme.primary,
                            color = MaterialTheme.colorScheme.onPrimary,
                            fontWeight = FontWeight.Bold
                        ),
                        highlitedPosition[0], highlitedPosition[1]
                    )
                ),
            ),
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth()
                .align(alignment = Alignment.CenterVertically),
        )
    }
}


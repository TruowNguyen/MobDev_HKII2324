package com.example.flightsearchapplication.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.flightsearchapplication.data.Airport

@Composable
fun AirportsList(
    modifier: Modifier = Modifier,
    airports: List<Airport>,
    navHostController: NavHostController,
    searchPhrase: String  = "",
    currentAirport: MutableState<Airport>
) {
    Card(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp, vertical = 5.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
        border = BorderStroke(2.dp, color = MaterialTheme.colorScheme.primary)
    ) {
        LazyColumn(modifier = modifier) {
            items(
                items = airports,
                key = { airport -> airport.id }
            ) { airport ->
                AirportRow(
                    airport = airport,
                    highlitedPosition =  highlitedPosition(
                        airport = airport,
                        searchPhrase = searchPhrase
                    ),
                    navHostController = navHostController,
                    currentAirport = currentAirport,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 5.dp, vertical = 8.dp)
                )
                Divider(color = MaterialTheme.colorScheme.onBackground, thickness = 2.dp)
            }
        }
    }
}

fun highlitedPosition(airport: Airport, searchPhrase: String): List<Int> {
    val first = "${airport.iataCode} ${airport.name}".lowercase().indexOf(searchPhrase)
    if (first < 0) {
        return listOf(0, 0)
    }
    return listOf(first, first + searchPhrase.length)
}
package com.example.flightsearchapplication.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.flightsearchapplication.R
import com.example.flightsearchapplication.data.Airport
import com.example.flightsearchapplication.ui.theme.FlightSearchApplicationTheme
import kotlinx.coroutines.launch


@Composable
fun AirportScreen(
    currentAirport: Airport,
    paddingValues: PaddingValues
) {
    val airportScreenViewModel: AirportScreenViewModel = viewModel(
        factory = AirportScreenViewModel.Factory
    )
    val airports =
        airportScreenViewModel
            .getRelatedAirports(currentAirport.id, currentAirport.iataCode)
            .collectAsState(emptyList()).value
    val coroutineScope = rememberCoroutineScope()
    Column(
        modifier = Modifier.padding(paddingValues)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 8.dp),
            shape = RoundedCornerShape(4.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
            border = BorderStroke(2.dp, color = MaterialTheme.colorScheme.primary)
        ) {
            Row(
                modifier = Modifier
                    .padding(horizontal = 5.dp, vertical = 10.dp)
            ) {

                Text(text = currentAirport.name)
            }

        }
        Spacer(modifier = Modifier.height(5.dp))
        Row {
            Text(text = stringResource(id = R.string.text_to))
        }
        Column() {
            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 10.dp, vertical = 5.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
                border = BorderStroke(2.dp, color = MaterialTheme.colorScheme.primary)
            ) {
                LazyColumn(modifier = Modifier) {
                    items(
                        items = airports,
                        key = { airport -> airport.id }
                    ) { airport ->
                        Row(modifier = Modifier.fillMaxWidth()) {
                            IconButton(onClick = {
                                coroutineScope.launch {
                                    if (airport.isFavorite) {
                                        airportScreenViewModel.deleteFavorite(
                                            favoriteId = airport.favoriteId,
                                            departureCode = currentAirport.iataCode,
                                            destinationCode = airport.iataCode
                                        )
                                    } else {
                                        airportScreenViewModel.addFavorite(
                                            currentAirport.iataCode,
                                            airport.iataCode
                                        )
                                    }
                                }
                            }) {
                                Icon(
                                    imageVector = if (airport.isFavorite) {
                                        Icons.Outlined.Star
                                    } else {
                                        Icons.Outlined.Add
                                    },
                                    contentDescription = stringResource(id = R.string.favorite_button),
                                    modifier = Modifier.semantics {
                                        testTag =
                                            if (airport.isFavorite) "Favorite" else "NonFavorite"
                                    }
                                )
                            }
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(
                                text = "${airport.iataCode} ${airport.name}",
                                modifier = Modifier
                                    .padding(5.dp)
                                    .fillMaxWidth()
                                    .align(alignment = Alignment.CenterVertically)
                            )
                        }
                        Divider(color = MaterialTheme.colorScheme.onBackground, thickness = 2.dp)
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true, apiLevel = 31)
@Composable
fun AirportScreenPreview() {
    FlightSearchApplicationTheme {
        AirportScreen(
            currentAirport = Airport(
                iataCode = "CDG",
                name = "Aéroport de Paris-Charles de Gaulle",
                passengers = 100
            ),
            paddingValues = PaddingValues()
        )
    }
}

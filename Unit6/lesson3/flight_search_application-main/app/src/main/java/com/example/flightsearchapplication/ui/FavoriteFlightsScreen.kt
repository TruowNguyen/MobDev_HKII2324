package com.example.flightsearchapplication.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.flightsearchapplication.R


@Composable
fun FavoriteScreen(
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier
) {

    val favoriteFlightsScreenViewModel: FavoriteFlightsScreenViewModel = viewModel(
        factory = FavoriteFlightsScreenViewModel.Factory
    )
    val favoriteAirports = favoriteFlightsScreenViewModel
        .getFavoriteAirport()
        .collectAsState(emptyList()).value
    Column(modifier = modifier.padding(paddingValues)) {
        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp, vertical = 5.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
            border = BorderStroke(2.dp, color = MaterialTheme.colorScheme.primary)
        ) {
            LazyColumn(

            ) {
                items(
                    items = favoriteAirports,
                    key = { airport -> airport.id }
                ) { airport ->
                    Row(modifier = Modifier.fillMaxWidth()) {
                        IconButton(onClick = {
                            favoriteFlightsScreenViewModel.deleteFavorite(
                                airport.id,
                                airport.departureCode,
                                airport.destinationCode
                            )
                        }
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Star,
                                contentDescription = stringResource(id = R.string.favorite_button)
                            )
                        }
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(text = "${airport.departureCode} ${airport.departureName} -> ${airport.destinationCode} ${airport.destinationName}")
                    }
                    Divider(color = MaterialTheme.colorScheme.onBackground, thickness = 2.dp)
                }
            }
        }
    }
}
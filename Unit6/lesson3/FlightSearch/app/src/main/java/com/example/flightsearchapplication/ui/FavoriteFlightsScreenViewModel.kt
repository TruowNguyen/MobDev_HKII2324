package com.example.flightsearchapplication.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.flightsearchapplication.FlightSearchApplication
import com.example.flightsearchapplication.data.Favorite
import com.example.flightsearchapplication.data.FavoriteAirportDao
import com.example.flightsearchapplication.data.FavoriteDao
import com.example.flightsearchapplication.data.FavoriteFlight
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class FavoriteFlightsScreenViewModel(
    private val favoriteAirportDao: FavoriteAirportDao,
    private val favoriteDao: FavoriteDao
) : ViewModel() {

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as FlightSearchApplication)
                FavoriteFlightsScreenViewModel(
                    application.database.favoriteAirportDao(),
                    application.database.favoriteDao()
                )
            }
        }
    }

    fun getFavoriteAirport(): Flow<List<FavoriteFlight>> =
        favoriteAirportDao.getFavoriteAirport()

    fun deleteFavorite(id: Int, departureCode: String, destinationCode: String) {
        viewModelScope.launch {
            favoriteDao.delete(
                Favorite(
                    id = id,
                    departureCode = departureCode,
                    destinationCode = destinationCode
                )
            )
        }
    }
}

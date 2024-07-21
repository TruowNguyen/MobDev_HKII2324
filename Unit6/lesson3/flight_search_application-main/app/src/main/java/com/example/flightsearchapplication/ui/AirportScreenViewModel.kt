package com.example.flightsearchapplication.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.flightsearchapplication.FlightSearchApplication
import com.example.flightsearchapplication.data.AirportFavorite
import com.example.flightsearchapplication.data.AirportFavoriteDao
import com.example.flightsearchapplication.data.Favorite
import com.example.flightsearchapplication.data.FavoriteDao
import kotlinx.coroutines.flow.Flow

class AirportScreenViewModel(

    private val favoriteDao: FavoriteDao,
    private val airportFavoriteDao: AirportFavoriteDao
) : ViewModel() {

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as FlightSearchApplication)
                AirportScreenViewModel(
                    application.database.favoriteDao(),
                    application.database.airportFavoriteDao()
                )
            }
        }
    }

    fun getRelatedAirports(id: Int, iataCode: String): Flow<List<AirportFavorite>> =
        airportFavoriteDao.getRelatedAirports(id, iataCode)

    suspend fun addFavorite(
        departureCode: String,
        destinationCode: String
    ) {
        favoriteDao.insert(
            Favorite(
                departureCode = departureCode,
                destinationCode = destinationCode
            )
        )
    }

    suspend fun deleteFavorite(favoriteId: Int, departureCode: String, destinationCode: String) {
        favoriteDao.delete(
            Favorite(
                id = favoriteId,
                departureCode = departureCode,
                destinationCode = destinationCode
            )
        )
    }
}
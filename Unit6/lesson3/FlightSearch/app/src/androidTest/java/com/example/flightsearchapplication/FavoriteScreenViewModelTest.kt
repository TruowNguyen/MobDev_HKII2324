package com.example.flightsearchapplication

import com.example.flightsearchapplication.data.Airport
import com.example.flightsearchapplication.data.AirportFavorite
import com.example.flightsearchapplication.data.AirportFavoriteDao
import com.example.flightsearchapplication.data.FavoriteAirportDao
import com.example.flightsearchapplication.ui.AirportScreenViewModel
import com.example.flightsearchapplication.ui.FavoriteFlightsScreenViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class FavoriteScreenViewModelTest: FlightSearchScreenViewModelTest() {
    private lateinit var favoriteFlightsScreenViewModel: FavoriteFlightsScreenViewModel
    private lateinit var favoriteAirportDao: FavoriteAirportDao
    private lateinit var airportFavoriteDao: AirportFavoriteDao
    private lateinit var airportScreenViewModel: AirportScreenViewModel
    override fun createDb(){
        super.createDb()
        favoriteAirportDao = db.favoriteAirportDao()
        airportFavoriteDao = db.airportFavoriteDao()
        favoriteFlightsScreenViewModel = FavoriteFlightsScreenViewModel(favoriteAirportDao = favoriteAirportDao, favoriteDao = db.favoriteDao())
        airportScreenViewModel = AirportScreenViewModel(favoriteDao = db.favoriteDao(), airportFavoriteDao = airportFavoriteDao)
    }
    @Test
    fun makeFlightFavoriteTest(){
        var firstAirport:Airport
        var secondAirport: AirportFavorite
        runBlocking {
            firstAirport = airportDao.getAirportsLike("aaa").first()[0]
            secondAirport= airportFavoriteDao.getRelatedAirports(
                firstAirport.id,
                firstAirport.iataCode
            ).first()[0]
            airportScreenViewModel.addFavorite(firstAirport.iataCode, secondAirport.iataCode)
            val favoriteFlight = favoriteFlightsScreenViewModel.getFavoriteAirport().first()[0]
            assert(favoriteFlight.departureCode == firstAirport.iataCode
                    && favoriteFlight.destinationCode == secondAirport.iataCode)
        }


    }
}
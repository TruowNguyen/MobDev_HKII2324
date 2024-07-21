package com.example.flightsearchapplication.data

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AirportFavoriteDao {

    @Query("Select airport.*, " +
            "iif(favorite.id is null, false, true) as isFavorite," +
            "ifnull(favorite.id, 0) as favoriteId " +
            "from (Select * from airport where id != :airportId) as airport " +
            "left join favorite as favorite " +
            "on favorite.departure_code = :airportIataCode and " +
            "airport.iata_code = favorite.destination_code")
    fun getRelatedAirports(airportId: Int, airportIataCode: String): Flow<List<AirportFavorite>>
}
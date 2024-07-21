package com.example.flightsearchapplication.data

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteAirportDao {

    @Query("select favorite.id, " +
            " favorite.departure_code as departureCode, " +
            " airport_dep.name as departureName, " +
            " favorite.destination_code as destinationCode, " +
            " airport_dest.name as destinationName " +
            "from favorite as favorite" +
            " join airport as airport_dep" +
            " on favorite.departure_code = airport_dep.iata_code " +
            " join airport as airport_dest " +
            " on favorite.destination_code = airport_dest.iata_code")
    fun getFavoriteAirport(): Flow<List<FavoriteFlight>>
}
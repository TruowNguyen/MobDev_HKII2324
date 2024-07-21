package com.example.flightsearchapplication.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AirportDao {

    @Query("select * from airport where iata_code like :searchPhrase or name like :searchPhrase")
    fun getAirportsLike(searchPhrase: String): Flow<List<Airport>>

    @Insert
    fun insert(airport: Airport)

}
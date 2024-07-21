package com.example.flightsearchapplication.data

import androidx.room.ColumnInfo
import org.jetbrains.annotations.NotNull

data class AirportFavorite(
    val id: Int,

    @ColumnInfo(name = "iata_code")
    val iataCode: String,

    @NotNull
    val name: String,

    val isFavorite: Boolean = false,

    val favoriteId: Int = 0

)
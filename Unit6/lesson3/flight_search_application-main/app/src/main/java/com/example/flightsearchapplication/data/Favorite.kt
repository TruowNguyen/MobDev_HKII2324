package com.example.flightsearchapplication.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "favorite")
data class Favorite(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @NotNull
    @ColumnInfo(name = "departure_code")
    val departureCode: String,

    @NotNull
    @ColumnInfo(name = "destination_code")
    val destinationCode: String,
)

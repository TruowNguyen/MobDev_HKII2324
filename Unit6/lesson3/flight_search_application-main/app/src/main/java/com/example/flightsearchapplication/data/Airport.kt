package com.example.flightsearchapplication.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull
import androidx.room.ColumnInfo

@Entity(tableName = "airport")
data class Airport(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @NotNull
    @ColumnInfo(name = "iata_code")
    val iataCode: String,

    @NotNull
    val name: String,

    @NotNull
    val passengers: Int,

)

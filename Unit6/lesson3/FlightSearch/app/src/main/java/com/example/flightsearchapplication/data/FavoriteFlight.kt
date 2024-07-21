package com.example.flightsearchapplication.data

data class FavoriteFlight(
    val id: Int,
    val departureCode: String,
    val departureName: String,
    val destinationCode: String,
    val destinationName: String
)

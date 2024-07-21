package com.example.flightsearchapplication.data

object AirportsDataProvider {
    val defaultAirport = getAirportsData()[0]
    fun getAirportsData(): List<Airport> {
        return listOf(
            Airport(
                id = 1,
                iataCode = "AAA",
                name = "Anaa Airport",
                passengers = 20
            ),
            Airport(
                id = 2,
                iataCode = "BAA",
                name = "Bialla Airport",
                passengers = 20
            ),
            Airport(
                id = 3,
                iataCode = "CAA",
                name = "El Aguacate Airport",
                passengers = 20
            ),
            Airport(
                id = 4,
                iataCode = "DAA",
                name = "Davison Army Airfield",
                passengers = 20
            ),
            Airport(
                id = 5,
                iataCode = "EAA",
                name = "Eagle Airport",
                passengers = 20
            ), Airport(
                id = 6,
                iataCode = "FAA",
                name = "Faranah Airport",
                passengers = 20
            )

        )
    }
}
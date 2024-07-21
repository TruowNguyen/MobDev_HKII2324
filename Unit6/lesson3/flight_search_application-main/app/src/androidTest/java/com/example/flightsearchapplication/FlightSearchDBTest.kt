package com.example.flightsearchapplication

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.flightsearchapplication.data.Airport
import com.example.flightsearchapplication.data.AirportDao
import com.example.flightsearchapplication.data.FlightDatabase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException


@RunWith(AndroidJUnit4::class)
class FlightSearchDBTest {

    private lateinit var airportDao: AirportDao
    private lateinit var db: FlightDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room
            .inMemoryDatabaseBuilder(
            context, FlightDatabase::class.java).build()
        airportDao = db.airportDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun readAirportsInList() = runBlocking{


        val airport: Airport = Airport(
            id =1,
            iataCode = "ISD",
            name = "Airport",
            passengers = 1
        )

        airportDao.insert(airport)

        val byName = airportDao.getAirportsLike("%rpo%").first()

        assertEquals(byName[0], airport)
    }

}
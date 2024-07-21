package com.example.flightsearchapplication


import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.flightsearchapplication.data.Airport
import com.example.flightsearchapplication.data.AirportDao
import com.example.flightsearchapplication.data.AirportsDataProvider
import com.example.flightsearchapplication.data.FavoriteDao
import com.example.flightsearchapplication.data.FlightDatabase
import com.example.flightsearchapplication.data.UserInputRepository
import com.example.flightsearchapplication.ui.FlightSearchScreenViewModel
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

private const val TEST_DATASTORE_NAME: String = "test_datastore"

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
open class FlightSearchScreenViewModelTest {
    lateinit var airportDao: AirportDao
    private lateinit var favoriteDao: FavoriteDao
    lateinit var db: FlightDatabase
    private val testCoroutineDispatcher = StandardTestDispatcher()
    private val testCoroutineScope = CoroutineScope(testCoroutineDispatcher + Job())

    private val testContext: Context = ApplicationProvider.getApplicationContext()
    private val testDataStore: DataStore<Preferences> =
        PreferenceDataStoreFactory.create(
            scope = testCoroutineScope,
            produceFile = { testContext.preferencesDataStoreFile(TEST_DATASTORE_NAME) }
        )
    private val repository: UserInputRepository =
        UserInputRepository(testDataStore)

    lateinit var viewModel: FlightSearchScreenViewModel

    @Before
    open fun createDb() {
        db = Room
            .inMemoryDatabaseBuilder(
                testContext, FlightDatabase::class.java
            ).build()
        airportDao = db.airportDao()
        favoriteDao = db.favoriteDao()
        fillDBWithAirports(airportDao)
        viewModel = FlightSearchScreenViewModel(userInputRepository = repository, airportDao)
    }

    @After
    fun cleanUp() {
        testContext.preferencesDataStoreFile(TEST_DATASTORE_NAME).delete()
    }

    @Test
    fun repositorySaveFetchSearchPhraseTest(): Unit {
        val searchPhrase = "hello"

        testCoroutineScope.launch {
            repository.saveSearchPhrase(searchPhrase = searchPhrase)
            val fetchedSearchPhrase = repository.searchPhrase
            assertEquals(searchPhrase, fetchedSearchPhrase)
        }
    }

    @Test
    fun getAirportsLikeTest(){
        val searchPhrase = "aaa"
        var airport: Airport
        runBlocking {
            val airports = viewModel.getAirportsLike(searchPhrase = searchPhrase).first()
            airport = airports[0]
            assertEquals(airport, AirportsDataProvider.defaultAirport)
        }
    }

    private fun fillDBWithAirports(airportDao: AirportDao){
        val airports = AirportsDataProvider.getAirportsData()
        airports.forEach {
            airport -> airportDao.insert(airport)
        }
    }

}


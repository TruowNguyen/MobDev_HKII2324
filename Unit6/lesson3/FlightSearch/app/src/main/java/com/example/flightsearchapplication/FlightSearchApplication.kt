package com.example.flightsearchapplication

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.flightsearchapplication.data.FlightDatabase
import com.example.flightsearchapplication.data.UserInputRepository

private const val SEARCH_PREFERENCE_NAME = "search_preferences"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = SEARCH_PREFERENCE_NAME
)

class FlightSearchApplication: Application() {
    lateinit var userInputRepository: UserInputRepository
    val database: FlightDatabase by lazy { FlightDatabase.getInstance(this) }
    override fun onCreate() {
        super.onCreate()
        userInputRepository = UserInputRepository(dataStore)
    }
}
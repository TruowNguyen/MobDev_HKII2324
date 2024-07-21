package com.example.flightsearchapplication.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class UserInputRepository(
    private val dataStore: DataStore<Preferences>
) {
    private companion object {
        val SEARCH_PHRASE = stringPreferencesKey("search_phrase")
    }

    suspend fun saveSearchPhrase(searchPhrase: String) {
        dataStore.edit { preferences ->
            preferences[SEARCH_PHRASE] = searchPhrase
        }
    }

    val searchPhrase: Flow<String> = dataStore.data
        .catch {
            if(it is IOException) {
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { preferences ->
            preferences[SEARCH_PHRASE] ?: ""
        }
}
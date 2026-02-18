package com.example.weatherappandroidcompose.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "city_prefs")

class CityDataStore(private val context: Context) {

    companion object {
        private val CITY_HISTORY_KEY = stringPreferencesKey("city_history")
        private const val DEFAULT_CITY = "Manila"
        private const val DELIMITER = ","
    }

    val cityHistory: Flow<List<String>> = context.dataStore.data
        .map { preferences ->
            val stored = preferences[CITY_HISTORY_KEY]
            if (stored.isNullOrBlank()) {
                listOf(DEFAULT_CITY)
            } else {
                stored.split(DELIMITER).filter { it.isNotBlank() }
            }
        }

    suspend fun addCity(city: String) {
        context.dataStore.edit { preferences ->
            val stored = preferences[CITY_HISTORY_KEY] ?: ""
            val currentList = if (stored.isBlank()) {
                listOf(DEFAULT_CITY)
            } else {
                stored.split(DELIMITER).filter { it.isNotBlank() }
            }

            if (!currentList.contains(city)) {
                val updated = currentList + city
                preferences[CITY_HISTORY_KEY] = updated.joinToString(DELIMITER)
            }
        }
    }

    suspend fun clearHistory() {
        context.dataStore.edit { preferences ->
            preferences[CITY_HISTORY_KEY] = DEFAULT_CITY
        }
    }
}
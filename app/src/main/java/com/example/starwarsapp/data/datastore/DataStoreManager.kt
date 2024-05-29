package com.example.starwarsapp.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.starwarsapp.domain.utils.AppThemes
import javax.inject.Inject

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "swappPreferences")

class DataStoreManager @Inject constructor(private val dataStore: DataStore<Preferences>) {

    private val APP_THEME = stringPreferencesKey("app_themes")

    val getCurrentTheme: Flow<String?> = dataStore.data
        .map { preferences ->
            preferences[APP_THEME] ?: AppThemes.EMPIRE
        }

    suspend fun setCurrentTheme(value: String) {
        dataStore.edit { preferences ->
            preferences[APP_THEME] = value
        }
    }
}
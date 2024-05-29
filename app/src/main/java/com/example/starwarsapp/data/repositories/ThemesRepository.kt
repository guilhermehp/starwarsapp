package com.example.starwarsapp.data.repositories

import com.example.starwarsapp.data.datastore.DataStoreManager
import com.example.starwarsapp.data.retrofit.ApiService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ThemesRepository @Inject constructor(private val dataStoreManager: DataStoreManager) {

    suspend fun setTheme(theme: String){
        dataStoreManager.setCurrentTheme(theme)
    }

    fun getCurrentTheme(): Flow<String?> {
        return dataStoreManager.getCurrentTheme
    }

}

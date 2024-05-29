package com.example.starwarsapp.data.repositories

import com.example.starwarsapp.data.models.Favorite
import com.example.starwarsapp.data.room.FavoriteDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavoriteRepository @Inject constructor(private val favoriteDao: FavoriteDao) {
    suspend fun insert(favorite: Favorite) {
        favoriteDao.insert(favorite)
    }

    suspend fun delete(favorite: Favorite) {
        favoriteDao.delete(favorite)
    }

    fun getAllFavorites(): Flow<List<Favorite>> {
        return favoriteDao.getAllFavorites()
    }

    fun getFavoriteByUrl(url: String): Flow<Favorite?> {
        return favoriteDao.getFavoriteByUrl(url)
    }
}
package com.example.starwarsapp.mocks

import com.example.starwarsapp.data.models.Favorite
import com.example.starwarsapp.data.models.FavoriteType
import com.example.starwarsapp.data.room.FavoriteDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeFavoriteDao: FavoriteDao {


    override suspend fun insert(favorite: Favorite) {}

    override suspend fun delete(favorite: Favorite) { }

    override fun getAllFavorites(): Flow<List<Favorite>> {
        return flow {
            val mockList = listOf(
                Favorite(
                    name = "A New Hope",
                    url = "https://swapi.dev/api/films/1/",
                    type = FavoriteType.FILM
                ),
                Favorite(
                    name = "Luke Skywalker",
                    url = "https://swapi.dev/api/people/1/",
                    type = FavoriteType.PEOPLE
                ),
                Favorite(
                    name = "Tatooine",
                    url = "https://swapi.dev/api/planets/1/",
                    type = FavoriteType.PLANET
                ),
                Favorite(
                    name = "Human",
                    url = "https://swapi.dev/api/species/1/",
                    type = FavoriteType.SPECIES
                ),
                Favorite(
                    name = "CR90 corvette",
                    url = "https://swapi.dev/api/starships/2/",
                    type = FavoriteType.STARSHIP
                ),
                Favorite(
                    name = "Sand Crawler",
                    url = "https://swapi.dev/api/vehicles/4/",
                    type = FavoriteType.VEHICLE
                )
            )
            emit(mockList)
        }
    }

    override fun getFavoriteByUrl(url: String): Flow<Favorite?> {
        return flow {
            emit(null)
        }
    }
}
package com.example.starwarsapp.domain.usecases

import androidx.compose.ui.graphics.vector.ImageVector
import androidx.room.Insert
import com.example.starwarsapp.data.models.Favorite
import com.example.starwarsapp.data.models.Film
import com.example.starwarsapp.data.models.People
import com.example.starwarsapp.data.models.Planet
import com.example.starwarsapp.data.models.Species
import com.example.starwarsapp.data.models.Starship
import com.example.starwarsapp.data.models.Vehicle
import com.example.starwarsapp.data.repositories.FavoriteRepository
import com.example.starwarsapp.data.repositories.SpeciesRepository
import com.example.starwarsapp.domain.utils.ModelUtils
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavoritesUseCase @Inject constructor(
    private val favoritesRepository: FavoriteRepository
) {

    suspend fun insertFilm(film: Film) {
        favoritesRepository.insert(ModelUtils.filmToFavorite(film))
    }

    suspend fun deleteFilm(film: Film){
        favoritesRepository.delete(ModelUtils.filmToFavorite(film))
    }

    suspend fun insertPeople(people: People) {
        favoritesRepository.insert(ModelUtils.peopleToFavorite(people))
    }

    suspend fun deletePeople(people: People){
        favoritesRepository.delete(ModelUtils.peopleToFavorite(people))
    }

    suspend fun insertPlanet(planet: Planet) {
        favoritesRepository.insert(ModelUtils.planetToFavorite(planet))
    }

    suspend fun deletePlanet(planet: Planet){
        favoritesRepository.delete(ModelUtils.planetToFavorite(planet))
    }

    suspend fun insertSpecies(species: Species) {
        favoritesRepository.insert(ModelUtils.speciesToFavorite(species))
    }

    suspend fun deleteSpecies(species: Species){
        favoritesRepository.delete(ModelUtils.speciesToFavorite(species))
    }

    suspend fun insertStarship(starship: Starship) {
        favoritesRepository.insert(ModelUtils.starshipToFavorite(starship))
    }

    suspend fun deleteStarship(starship: Starship){
        favoritesRepository.delete(ModelUtils.starshipToFavorite(starship))
    }

    suspend fun insertVehicle(vehicle: Vehicle) {
        favoritesRepository.insert(ModelUtils.vehicleToFavorite(vehicle))
    }

    suspend fun deleteVehicle(vehicle: Vehicle){
        favoritesRepository.delete(ModelUtils.vehicleToFavorite(vehicle))
    }

    fun getAllFavorites(): Flow<List<Favorite>> {
        return favoritesRepository.getAllFavorites()
    }

    fun getFavoriteByUrl(url: String): Flow<Favorite?> {
        return favoritesRepository.getFavoriteByUrl(url)
    }

}
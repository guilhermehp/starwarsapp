package com.example.starwarsapp.domain.utils

import com.example.starwarsapp.data.models.Favorite
import com.example.starwarsapp.data.models.FavoriteType
import com.example.starwarsapp.data.models.Film
import com.example.starwarsapp.data.models.People
import com.example.starwarsapp.data.models.Planet
import com.example.starwarsapp.data.models.Species
import com.example.starwarsapp.data.models.Starship
import com.example.starwarsapp.data.models.Vehicle

object ModelUtils {
    fun filmToFavorite(film: Film): Favorite {
        return Favorite(
            url = film.url,
            name = film.title,
            type = FavoriteType.FILM
        )
    }

    fun peopleToFavorite(people: People): Favorite {
        return Favorite(
            url = people.url,
            name = people.name,
            type = FavoriteType.PEOPLE
        )
    }

    fun planetToFavorite(planet: Planet): Favorite {
        return Favorite(
            url = planet.url,
            name = planet.name,
            type = FavoriteType.PLANET
        )
    }

    fun speciesToFavorite(species: Species): Favorite {
        return Favorite(
            url = species.url,
            name = species.name,
            type = FavoriteType.SPECIES
        )
    }

    fun starshipToFavorite(starship: Starship): Favorite {
        return Favorite(
            url = starship.url,
            name = starship.name,
            type = FavoriteType.STARSHIP
        )
    }

    fun vehicleToFavorite(vehicle: Vehicle): Favorite {
        return Favorite(
            url = vehicle.url,
            name = vehicle.name,
            type = FavoriteType.VEHICLE
        )
    }
}
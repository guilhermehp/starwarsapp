package com.example.starwarsapp.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class Favorite (
    @PrimaryKey val url: String,
    val name: String,
    val type: FavoriteType

)

enum class FavoriteType {
    FILM, PEOPLE, PLANET, SPECIES, STARSHIP, VEHICLE
}
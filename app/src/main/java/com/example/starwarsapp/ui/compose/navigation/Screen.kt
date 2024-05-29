package com.example.starwarsapp.ui.compose.navigation

import android.net.Uri

sealed class Screen (val route: String) {
    data object Home: Screen("home")
    data object Favorites: Screen("favorites")
    data object FilmsDetails : Screen("films/{url}"){
        fun createRoute(url: String) : String {
            val encodedUrl = Uri.encode(url)
            return "films/$encodedUrl"
        }
    }
    data object PeopleDetails : Screen("people/{url}") {
        fun createRoute(url: String) : String {
            val encodedUrl = Uri.encode(url)
            return "people/$encodedUrl"
        }
    }
    data object PlanetDetails : Screen("planet/{url}") {
        fun createRoute(url: String) : String {
            val encodedUrl = Uri.encode(url)
            return "planet/$encodedUrl"
        }
    }
    data object SpeciesDetails : Screen("species/{url}") {
        fun createRoute(url: String) : String {
            val encodedUrl = Uri.encode(url)
            return "species/$encodedUrl"
        }
    }
    data object StarshipsDetails : Screen("starships/{url}") {
        fun createRoute(url: String) : String {
            val encodedUrl = Uri.encode(url)
            return "starships/$encodedUrl"
        }
    }
    data object VehiclesDetails : Screen("vehicles/{url}") {
        fun createRoute(url: String) : String {
            val encodedUrl = Uri.encode(url)
            return "vehicles/$encodedUrl"
        }
    }
}
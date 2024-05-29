package com.example.starwarsapp.ui.compose.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.starwarsapp.ui.compose.detailsscreens.details.FilmsDetailScreen
import com.example.starwarsapp.ui.compose.detailsscreens.details.PeopleDetailScreen
import com.example.starwarsapp.ui.compose.detailsscreens.details.PlanetDetailsScreen
import com.example.starwarsapp.ui.compose.detailsscreens.details.SpeciesDetailsScreen
import com.example.starwarsapp.ui.compose.detailsscreens.details.StarshipsDetailsScreen
import com.example.starwarsapp.ui.compose.detailsscreens.details.VehiclesDetailsScreen
import com.example.starwarsapp.ui.compose.listscreens.FavoriteListScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) { HomeScreen(navController) }
        composable(Screen.Favorites.route) { FavoriteListScreen(navController) }
        composable(Screen.PeopleDetails.route) { navBackStackEntry ->
            navBackStackEntry.arguments?.getString("url")?.let { url ->
                PeopleDetailScreen(url, navController)
            } ?: run {
                navController.popBackStack()
            }
        }
        composable(Screen.FilmsDetails.route) { navBackStackEntry ->
            navBackStackEntry.arguments?.getString("url")?.let { url ->
                FilmsDetailScreen(url, navController)
            } ?: run {
                navController.popBackStack()
            }
        }
        composable(Screen.PlanetDetails.route) { navBackStackEntry ->
            navBackStackEntry.arguments?.getString("url")?.let { url ->
                PlanetDetailsScreen(url, navController)
            } ?: run {
                navController.popBackStack()
            }
        }
        composable(Screen.SpeciesDetails.route) { navBackStackEntry ->
            navBackStackEntry.arguments?.getString("url")?.let { url ->
                SpeciesDetailsScreen(url, navController)
            } ?: run {
                navController.popBackStack()
            }
        }
        composable(Screen.StarshipsDetails.route) { navBackStackEntry ->
            navBackStackEntry.arguments?.getString("url")?.let { url ->
                StarshipsDetailsScreen(url, navController)
            } ?: run {
                navController.popBackStack()
            }
        }
        composable(Screen.VehiclesDetails.route) { navBackStackEntry ->
            navBackStackEntry.arguments?.getString("url")?.let { url ->
                VehiclesDetailsScreen(url, navController)
            } ?: run {
                navController.popBackStack()
            }
        }
    }
}
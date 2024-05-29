package com.example.starwarsapp.ui.compose.listscreens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.starwarsapp.R
import com.example.starwarsapp.data.models.Favorite
import com.example.starwarsapp.data.models.FavoriteType
import com.example.starwarsapp.ui.compose.navigation.Screen
import com.example.starwarsapp.ui.viewmodels.listviewmodels.FavoritesViewModel

@Composable
fun FavoriteListScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    favoritesViewModel: FavoritesViewModel = hiltViewModel()
) {

    val favorites = favoritesViewModel.favorites.collectAsState().value

    LaunchedEffect(0) {
        favoritesViewModel.getAllFavorites()
    }

    Scaffold(
        topBar = {
            FavoritesTopBar(navController, favoritesViewModel)
        },
        content = { paddingValues ->
            if(favorites.isEmpty()){
                Column(
                    Modifier.fillMaxSize().padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = stringResource(id = R.string.no_data_found),
                        style = MaterialTheme.typography.headlineLarge,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            } else {
                FavoritesList(
                    modifier = modifier,
                    paddingValues = paddingValues,
                    favorites = favorites,
                    navController = navController)
            }
        }
    )
}

@Composable
fun FavoritesList(
    modifier: Modifier,
    paddingValues: PaddingValues,
    favorites: List<Favorite>,
    navController: NavController

){
    LazyColumn(
        modifier = modifier
            .background(color = MaterialTheme.colorScheme.background)
            .padding(paddingValues)
    ) {
        items(favorites) { favorite ->
            Column(
                modifier = Modifier
                    .clickable {
                        when (favorite.type) {
                            FavoriteType.FILM -> navController.navigate(
                                Screen.FilmsDetails.createRoute(
                                    favorite.url
                                )
                            )

                            FavoriteType.PEOPLE -> navController.navigate(
                                Screen.PeopleDetails.createRoute(
                                    favorite.url
                                )
                            )

                            FavoriteType.PLANET -> navController.navigate(
                                Screen.PlanetDetails.createRoute(
                                    favorite.url
                                )
                            )

                            FavoriteType.SPECIES -> navController.navigate(
                                Screen.SpeciesDetails.createRoute(
                                    favorite.url
                                )
                            )

                            FavoriteType.STARSHIP -> navController.navigate(
                                Screen.StarshipsDetails.createRoute(
                                    favorite.url
                                )
                            )

                            FavoriteType.VEHICLE -> navController.navigate(
                                Screen.VehiclesDetails.createRoute(
                                    favorite.url
                                )
                            )
                        }

                    }
            ) {
                Text(
                    text = favorite.name.lowercase(),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.tertiary,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
                HorizontalDivider(
                    thickness = 0.50.dp,
                    color = MaterialTheme.colorScheme.tertiary
                )
            }
        }
    }

}

@Composable
fun FavoritesTopBar(navController: NavController, favoritesViewModel: FavoritesViewModel) {
    var showMenu by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.primary)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,

            ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Back",
                    modifier = Modifier.size(24.dp),
                    tint = MaterialTheme.colorScheme.secondary
                )
            }
            Text(
                text = stringResource(id = R.string.favorites),
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.secondary
            )
            Box {
                IconButton(onClick = { showMenu = !showMenu }) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = "Filter",
                        modifier = Modifier.size(24.dp),
                        tint = MaterialTheme.colorScheme.secondary
                    )
                }
                DropdownMenu(
                    expanded = showMenu,
                    onDismissRequest = { showMenu = false }
                ) {

                    val people = stringResource(id = R.string.people)
                    val films = stringResource(id = R.string.films)
                    val planets = stringResource(id = R.string.planets)
                    val species = stringResource(id = R.string.species)
                    val starships = stringResource(id = R.string.starships)
                    val vehicles = stringResource(id = R.string.vehicles)
                    val reverse = stringResource(id = R.string.reverse)
                    val clear = stringResource(id = R.string.clear)

                    val filterOptions = listOf(people, films, planets, species, starships, vehicles, reverse, clear)
                    filterOptions.forEach { option ->
                        DropdownMenuItem(
                            onClick = {
                                showMenu = false
                                when(option) {
                                    people -> { favoritesViewModel.filterPeople() }
                                    films -> { favoritesViewModel.filterFilms() }
                                    planets -> { favoritesViewModel.filterPlanets() }
                                    species -> { favoritesViewModel.filterSpecies() }
                                    starships -> { favoritesViewModel.filterStarships() }
                                    vehicles -> { favoritesViewModel.filterVehicles() }
                                    reverse -> { favoritesViewModel.filterReverse() }
                                    clear -> { favoritesViewModel.clearFilter() }
                                }
                            }, text = {
                                Text(
                                    text = option,
                                    style = MaterialTheme.typography.labelMedium)
                            })
                    }
                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(2.dp)
                .background(MaterialTheme.colorScheme.secondary)
        )
    }
}
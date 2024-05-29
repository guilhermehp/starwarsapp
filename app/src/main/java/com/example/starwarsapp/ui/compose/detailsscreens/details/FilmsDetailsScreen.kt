package com.example.starwarsapp.ui.compose.detailsscreens.details

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandIn
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.starwarsapp.R
import com.example.starwarsapp.data.models.Film
import com.example.starwarsapp.data.models.People
import com.example.starwarsapp.data.models.Planet
import com.example.starwarsapp.data.models.Species
import com.example.starwarsapp.data.models.Starship
import com.example.starwarsapp.data.models.Vehicle
import com.example.starwarsapp.ui.compose.components.FloatingButtons
import com.example.starwarsapp.ui.compose.detailsscreens.items.PeopleItem
import com.example.starwarsapp.ui.compose.detailsscreens.items.PlanetsItem
import com.example.starwarsapp.ui.compose.detailsscreens.items.SpeciesItem
import com.example.starwarsapp.ui.compose.detailsscreens.items.StarshipItem
import com.example.starwarsapp.ui.compose.detailsscreens.items.VehiclesItem
import com.example.starwarsapp.ui.compose.components.LoadingOverlay
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.testTag
import com.example.starwarsapp.domain.utils.TestingTags
import com.example.starwarsapp.ui.compose.components.ContentFadeIn
import com.example.starwarsapp.ui.compose.components.HeaderExpander
import com.example.starwarsapp.ui.viewmodels.detailsviewmodels.FilmsDetailsViewModel

@Composable
fun FilmsDetailScreen(
    url: String,
    navController: NavController,
    filmsDetailsViewModel: FilmsDetailsViewModel = hiltViewModel()
) {

    val film = filmsDetailsViewModel.film.collectAsState().value
    val people = filmsDetailsViewModel.people.collectAsState().value
    val planets = filmsDetailsViewModel.planets.collectAsState().value
    val species = filmsDetailsViewModel.species.collectAsState().value
    val vehicles = filmsDetailsViewModel.vehicles.collectAsState().value
    val starships = filmsDetailsViewModel.starships.collectAsState().value
    val isAddedAsFavorite = filmsDetailsViewModel.isAddedAsFavorite.collectAsState().value
    val pendingRequests = filmsDetailsViewModel.pendingRequests.collectAsState().value
    val error = filmsDetailsViewModel.error.collectAsState().value
    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = url) {
        filmsDetailsViewModel.getFilmById(url)
    }

    if (error.isNotEmpty()) {
        LaunchedEffect(error) {
            snackBarHostState.showSnackbar(message = error)
        }
    }

    Scaffold(
        topBar = {
            HeaderExpander(isVisible = pendingRequests.isEmpty()) {
                FilmsDetailsTopBar(film = film)
            }
        },
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                LoadingOverlay(isLoading = pendingRequests.isEmpty().not()) {
                    ContentFadeIn(isVisible = pendingRequests.isEmpty()){
                        FilmsDetailsContent(
                            film = film,
                            people = people,
                            planets = planets,
                            starships = starships,
                            vehicles = vehicles,
                            species = species,
                            navController = navController
                        )
                    }

                }

                Column(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    FloatingButtons(
                        navController = navController,
                        isAddedAsFavorite = isAddedAsFavorite,
                        addToFavorites = {
                            filmsDetailsViewModel.addToFavorites()
                        },
                        removeFromFavorites = {
                            filmsDetailsViewModel.removeFromFavorites()
                        }
                    )
                }
            }

        }

    )

}

@Composable
fun FilmsDetailsTopBar(film: Film) {

    Column(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.primary),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Text(
                modifier = Modifier.padding(bottom = 8.dp),
                text = film.title.lowercase(),
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.secondary,
                textAlign = TextAlign.Center
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(2.dp)
                .background(MaterialTheme.colorScheme.secondary)
        )
    }


}

@Composable()
fun FilmsDetailsContent(
    film: Film,
    people: List<People>,
    planets: List<Planet>,
    starships: List<Starship>,
    vehicles: List<Vehicle>,
    species: List<Species>,
    navController: NavController
) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .testTag(TestingTags.FILMS_LIST)
        ) {
            item {
                FilmContent(film)
            }

            item {
                PeopleItem(people, navController = navController)
            }

            item {
                PlanetsItem(planets, navController)
            }

            item {
                StarshipItem(starships, navController)
            }

            item {
                VehiclesItem(vehicles, navController)
            }

            item {
                SpeciesItem(species, navController)
            }
        }
}

@Composable()
fun FilmContent(film: Film) {
    Column() {
        Text(
            text = stringResource(id = R.string.opening_crawl),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = film.opening_crawl.lowercase(),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier
                .fillMaxWidth()

        )
        Text(
            text = stringResource(id = R.string.director),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier
                .fillMaxWidth()

        )
        Text(
            text = film.director.lowercase(),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier
                .fillMaxWidth()

        )
        Text(
            text = stringResource(id = R.string.producer),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier
                .fillMaxWidth()

        )
        Text(
            text = film.producer.lowercase(),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier
                .fillMaxWidth()

        )
        Text(
            text = stringResource(id = R.string.release_date),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier
                .fillMaxWidth()

        )
        Text(
            text = film.release_date.lowercase(),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier
                .fillMaxWidth()

        )
    }

}


package com.example.starwarsapp.ui.compose.detailsscreens.details

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
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
import com.example.starwarsapp.domain.utils.TestingTags
import com.example.starwarsapp.ui.compose.components.ContentFadeIn
import com.example.starwarsapp.ui.compose.detailsscreens.items.FilmsItem
import com.example.starwarsapp.ui.compose.detailsscreens.items.PeopleItem
import com.example.starwarsapp.ui.compose.components.FloatingButtons
import com.example.starwarsapp.ui.compose.components.HeaderExpander
import com.example.starwarsapp.ui.compose.components.LoadingOverlay
import com.example.starwarsapp.ui.compose.navigation.Screen
import com.example.starwarsapp.ui.viewmodels.detailsviewmodels.SpeciesDetailsViewModel

@Composable
fun SpeciesDetailsScreen(
    url: String,
    navController: NavController,
    speciesDetailsViewModel: SpeciesDetailsViewModel = hiltViewModel()
) {

    val films = speciesDetailsViewModel.films.collectAsState().value
    val people = speciesDetailsViewModel.people.collectAsState().value
    val planet = speciesDetailsViewModel.planet.collectAsState().value
    val species = speciesDetailsViewModel.species.collectAsState().value
    val isAddedAsFavorite = speciesDetailsViewModel.isAddedAsFavorite.collectAsState().value
    val pendingRequests = speciesDetailsViewModel.pendingRequests.collectAsState().value
    val error = speciesDetailsViewModel.error.collectAsState().value
    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = url) {
        speciesDetailsViewModel.getSpeciesById(url)
    }

    if (error.isNotEmpty()) {
        LaunchedEffect(error) {
            snackBarHostState.showSnackbar(message = error)
        }
    }

    Scaffold(
        topBar = {
            HeaderExpander(isVisible = pendingRequests.isEmpty()) {
                SpeciesDetailsTopBar(species = species)
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
                    ContentFadeIn(isVisible = pendingRequests.isEmpty()) {
                        SpeciesDetailsContent(
                            species = species,
                            planet = planet,
                            people = people,
                            films = films,
                            navController = navController,
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
                            speciesDetailsViewModel.addToFavorites()
                        },
                        removeFromFavorites = {
                            speciesDetailsViewModel.removeFromFavorites()
                        }
                    )
                }
            }

        }

    )
}

@Composable()
fun SpeciesDetailsTopBar(
    species: Species
) {
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
                text = species.name.lowercase(),
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

@Composable
fun SpeciesDetailsContent(
    species: Species,
    planet: Planet,
    people: List<People>,
    films: List<Film>,
    navController: NavController
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .testTag(TestingTags.SPECIES_LIST)
    ) {
        item {
            SpeciesContent(species, planet, navController)
        }

        item {
            PeopleItem(people, navController = navController)
        }

        item {
            FilmsItem(films, navController)
        }
    }
}

@Composable()
fun SpeciesContent(species: Species, planet: Planet, navController: NavController) {
    Column {
        Text(
            text = stringResource(id = R.string.classification),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier
                .fillMaxWidth()

        )
        Text(
            text = species.classification.lowercase(),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier.fillMaxWidth()

        )
        Text(
            text = stringResource(id = R.string.designation),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = species.designation.lowercase(),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier.fillMaxWidth()

        )
        Text(
            text = stringResource(id = R.string.average_height),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.fillMaxWidth()

        )
        Text(
            text = species.average_height.lowercase(),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = stringResource(id = R.string.skin_color),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.fillMaxWidth()

        )
        Text(
            text = species.skin_colors.lowercase(),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = stringResource(id = R.string.hair_colors),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.fillMaxWidth()

        )
        Text(
            text = species.hair_colors.lowercase(),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = stringResource(id = R.string.eye_colors),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.fillMaxWidth()

        )
        Text(
            text = species.eye_colors.lowercase(),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = stringResource(id = R.string.average_lifespan),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.fillMaxWidth()

        )
        Text(
            text = species.average_lifespan.lowercase(),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = stringResource(id = R.string.language),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.fillMaxWidth()

        )
        Text(
            text = species.language.lowercase(),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = stringResource(id = R.string.home_world),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.fillMaxWidth()

        )
        Text(
            text = planet.name.lowercase(),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.inversePrimary,
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    navController.navigate(Screen.PlanetDetails.createRoute(planet.url))
                }
        )

    }

}
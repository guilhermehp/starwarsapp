package com.example.starwarsapp.ui.compose.detailsscreens.details

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
import com.example.starwarsapp.domain.utils.TestingTags
import com.example.starwarsapp.ui.compose.components.ContentFadeIn
import com.example.starwarsapp.ui.compose.detailsscreens.items.FilmsItem
import com.example.starwarsapp.ui.compose.detailsscreens.items.PeopleItem
import com.example.starwarsapp.ui.compose.components.FloatingButtons
import com.example.starwarsapp.ui.compose.components.HeaderExpander
import com.example.starwarsapp.ui.compose.components.LoadingOverlay
import com.example.starwarsapp.ui.viewmodels.detailsviewmodels.PlanetsDetailsViewModel

@Composable
fun PlanetDetailsScreen(
    url: String,
    navController: NavController,
    planetsDetailsViewModel: PlanetsDetailsViewModel = hiltViewModel()
) {

    val films = planetsDetailsViewModel.films.collectAsState().value
    val people = planetsDetailsViewModel.people.collectAsState().value
    val planet = planetsDetailsViewModel.planet.collectAsState().value
    val isAddedAsFavorite = planetsDetailsViewModel.isAddedAsFavorite.collectAsState().value
    val pendingRequests = planetsDetailsViewModel.pendingRequests.collectAsState().value
    val error = planetsDetailsViewModel.error.collectAsState().value
    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(url) {
        planetsDetailsViewModel.getPlanetById(url)
    }

    if(error.isNotEmpty()){
        LaunchedEffect(error) {
            snackBarHostState.showSnackbar(message = error)
        }
    }

    Scaffold(
        topBar = {
            HeaderExpander(isVisible = pendingRequests.isEmpty()) {
                PlanetsDetailsTopBar(planet = planet)
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
                        PlanetsDetailsContent(
                            planet = planet,
                            people = people,
                            films = films,
                            navController
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
                            planetsDetailsViewModel.addToFavorites()
                        },
                        removeFromFavorites = {
                            planetsDetailsViewModel.removeFromFavorites()
                        }
                    )
                }
            }

        }

    )

}

@Composable
fun PlanetsDetailsTopBar(planet: Planet){
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
                text = planet.name.lowercase(),
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
fun PlanetsDetailsContent(
    planet: Planet,
    people: List<People>,
    films: List<Film>,
    navController: NavController
){
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .testTag(TestingTags.PLANETS_LIST)
    ) {
        item {
            PlanetContent(planet)
        }

        item {
            PeopleItem(people, isPlanet = true, navController = navController)
        }

        item {
            FilmsItem(films, navController)
        }
    }
}

@Composable()
fun PlanetContent(planet: Planet) {
    Column {
        Text(
            text = stringResource(id = R.string.rotation_period),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier
                .fillMaxWidth()

        )
        Text(
            text = planet.rotation_period.lowercase(),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier.fillMaxWidth()

        )
        Text(
            text = stringResource(id = R.string.orbital_period),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = planet.orbital_period.lowercase(),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier.fillMaxWidth()

        )
        Text(
            text = stringResource(id = R.string.diameter),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.fillMaxWidth()

        )
        Text(
            text = planet.diameter.lowercase(),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = stringResource(id = R.string.climate),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.fillMaxWidth()

        )
        Text(
            text = planet.climate.lowercase(),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = stringResource(id = R.string.gravity),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.fillMaxWidth()

        )
        Text(
            text = planet.gravity.lowercase(),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = stringResource(id = R.string.terrain),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.fillMaxWidth()

        )
        Text(
            text = planet.terrain.lowercase(),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = stringResource(id = R.string.surface_water),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.fillMaxWidth()

        )
        Text(
            text = planet.surface_water.lowercase(),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = stringResource(id = R.string.population),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.fillMaxWidth()

        )
        Text(
            text = planet.population.lowercase(),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier.fillMaxWidth()
        )
    }

}
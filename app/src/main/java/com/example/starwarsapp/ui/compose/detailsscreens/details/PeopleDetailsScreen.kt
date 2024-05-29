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
import com.example.starwarsapp.data.models.Starship
import com.example.starwarsapp.data.models.Vehicle
import com.example.starwarsapp.domain.utils.TestingTags
import com.example.starwarsapp.ui.compose.components.ContentFadeIn
import com.example.starwarsapp.ui.compose.detailsscreens.items.FilmsItem
import com.example.starwarsapp.ui.compose.detailsscreens.items.SpeciesItem
import com.example.starwarsapp.ui.compose.detailsscreens.items.StarshipItem
import com.example.starwarsapp.ui.compose.detailsscreens.items.VehiclesItem
import com.example.starwarsapp.ui.compose.components.FloatingButtons
import com.example.starwarsapp.ui.compose.components.HeaderExpander
import com.example.starwarsapp.ui.compose.components.LoadingOverlay
import com.example.starwarsapp.ui.compose.navigation.Screen
import com.example.starwarsapp.ui.viewmodels.detailsviewmodels.PeopleDetailsViewModel

@Composable
fun PeopleDetailScreen(
    url: String,
    navController: NavController,
    peopleDetailsViewModel: PeopleDetailsViewModel = hiltViewModel()
) {

    val people = peopleDetailsViewModel.people.collectAsState().value
    val planet = peopleDetailsViewModel.planet.collectAsState().value
    val films = peopleDetailsViewModel.films.collectAsState().value
    val species = peopleDetailsViewModel.species.collectAsState().value
    val vehicles = peopleDetailsViewModel.vehicles.collectAsState().value
    val starships = peopleDetailsViewModel.starships.collectAsState().value
    val isAddedAsFavorite = peopleDetailsViewModel.isAddedAsFavorite.collectAsState().value
    val pendingRequests = peopleDetailsViewModel.pendingRequests.collectAsState().value
    val error = peopleDetailsViewModel.error.collectAsState().value
    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(url) {
        peopleDetailsViewModel.getPeopleById(url)
    }

    if(error.isNotEmpty()){
        LaunchedEffect(error) {
            snackBarHostState.showSnackbar(message = error)
        }
    }

    Scaffold(
        topBar = {
            HeaderExpander(isVisible = pendingRequests.isEmpty()) {
                PeopleDetailsTopBar(people = people)
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
                        PeopleDetailsContent(
                            people = people,
                            planet = planet,
                            films = films,
                            species = species,
                            vehicles = vehicles,
                            starships = starships,
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
                            peopleDetailsViewModel.addToFavorites()
                        },
                        removeFromFavorites = {
                            peopleDetailsViewModel.removeFromFavorites()
                        }
                    )
                }
            }

        })

}

@Composable
fun PeopleDetailsTopBar(people: People){
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
                text = people.name.lowercase(),
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
fun PeopleDetailsContent(
    people: People,
    planet: Planet,
    films: List<Film>,
    species: List<Species>,
    vehicles: List<Vehicle>,
    starships: List<Starship>,
    navController: NavController
){
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .testTag(TestingTags.PEOPLE_LIST)
    ) {

        item {
            PeopleContent(people, planet, navController)
        }

        item {
            FilmsItem(films, navController)
        }

        item {
            SpeciesItem(species, navController)
        }

        item {
            VehiclesItem(vehicles, navController)
        }

        item {
            StarshipItem(starships, navController)
        }
    }
}

@Composable()
fun PeopleContent(people: People, planet: Planet, navController: NavController) {
    Column {
        Text(
            text = stringResource(id = R.string.height),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = people.height.lowercase(),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier.fillMaxWidth()

        )
        Text(
            text = stringResource(id = R.string.mass),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.fillMaxWidth()

        )
        Text(
            text = people.mass.lowercase(),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier.fillMaxWidth()

        )
        Text(
            text = stringResource(id = R.string.hair_color),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.fillMaxWidth()

        )
        Text(
            text = people.hair_color.lowercase(),
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
            text = people.skin_color.lowercase(),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier.fillMaxWidth()

        )
        Text(
            text = stringResource(id = R.string.eye_color),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.fillMaxWidth()

        )
        Text(
            text = people.eye_color.lowercase(),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier.fillMaxWidth()

        )
        Text(
            text = stringResource(id = R.string.birth_year),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.fillMaxWidth()

        )
        Text(
            text = people.birth_year.lowercase(),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier.fillMaxWidth()

        )
        Text(
            text = stringResource(id = R.string.gender),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.fillMaxWidth()

        )
        Text(
            text = people.gender.lowercase(),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier.fillMaxWidth()

        )
        Text(
            text = stringResource(id = R.string.planet),
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


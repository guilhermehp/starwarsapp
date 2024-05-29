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
import com.example.starwarsapp.data.models.Vehicle
import com.example.starwarsapp.domain.utils.TestingTags
import com.example.starwarsapp.ui.compose.components.ContentFadeIn
import com.example.starwarsapp.ui.compose.detailsscreens.items.FilmsItem
import com.example.starwarsapp.ui.compose.detailsscreens.items.PeopleItem
import com.example.starwarsapp.ui.compose.components.FloatingButtons
import com.example.starwarsapp.ui.compose.components.HeaderExpander
import com.example.starwarsapp.ui.compose.components.LoadingOverlay
import com.example.starwarsapp.ui.viewmodels.detailsviewmodels.VehiclesDetailsViewModel


@Composable
fun VehiclesDetailsScreen(
    url: String,
    navController: NavController,
    vehiclesDetailsViewModel: VehiclesDetailsViewModel = hiltViewModel()
) {

    val films = vehiclesDetailsViewModel.films.collectAsState().value
    val people = vehiclesDetailsViewModel.people.collectAsState().value
    val vehicle = vehiclesDetailsViewModel.vehicle.collectAsState().value
    val isAddedAsFavorite = vehiclesDetailsViewModel.isAddedAsFavorite.collectAsState().value
    val pendingRequests = vehiclesDetailsViewModel.pendingRequests.collectAsState().value
    val error = vehiclesDetailsViewModel.error.collectAsState().value
    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = url) {
        vehiclesDetailsViewModel.getVehicleById(url)
    }

    if(error.isNotEmpty()){
        LaunchedEffect(error) {
            snackBarHostState.showSnackbar(message = error)
        }
    }

    Scaffold(
        topBar = {
            HeaderExpander(isVisible = pendingRequests.isEmpty()) {
                VehiclesDetailsTopBar(vehicle = vehicle)
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
                        VehicleDetailsContent(
                            vehicle = vehicle,
                            people = people,
                            films = films,
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
                            vehiclesDetailsViewModel.addToFavorites()
                        },
                        removeFromFavorites = {
                            vehiclesDetailsViewModel.removeFromFavorites()
                        }
                    )
                }
            }

        }

    )
}

@Composable
fun VehiclesDetailsTopBar(vehicle: Vehicle){
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
                text = vehicle.name.lowercase(),
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
fun VehicleDetailsContent(
    vehicle: Vehicle,
    people: List<People>,
    films: List<Film>,
    navController: NavController
){
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .testTag(TestingTags.VEHICLES_LIST)
    ) {
        item {
            VehicleContent(vehicle)
        }

        item {
            PeopleItem(people, isPilot = true, navController = navController)
        }

        item {
            FilmsItem(films, navController)
        }
    }
}

@Composable()
fun VehicleContent(vehicle: Vehicle) {
    Column {
        Text(
            text = stringResource(id = R.string.model),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier
                .fillMaxWidth()

        )
        Text(
            text = vehicle.model.lowercase(),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = stringResource(id = R.string.manufacturer),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = vehicle.manufacturer.lowercase(),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier.fillMaxWidth()

        )
        Text(
            text = stringResource(id = R.string.cost_in_credits),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.fillMaxWidth()

        )
        Text(
            text = vehicle.cost_in_credits.lowercase(),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = stringResource(id = R.string.length),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.fillMaxWidth()

        )
        Text(
            text = vehicle.length.lowercase(),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = stringResource(id = R.string.max_atmosphering_speed),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.fillMaxWidth()

        )
        Text(
            text = vehicle.max_atmosphering_speed.lowercase(),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = stringResource(id = R.string.crew),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.fillMaxWidth()

        )
        Text(
            text = vehicle.crew.lowercase(),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = stringResource(id = R.string.passengers),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.fillMaxWidth()

        )
        Text(
            text = vehicle.crew.lowercase(),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = stringResource(id = R.string.passengers),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.fillMaxWidth()

        )
        Text(
            text = vehicle.passengers.lowercase(),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = stringResource(id = R.string.cargo_capacity),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.fillMaxWidth()

        )
        Text(
            text = vehicle.cargo_capacity.lowercase(),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = stringResource(id = R.string.consumables),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.fillMaxWidth()

        )
        Text(
            text = vehicle.consumables.lowercase(),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = stringResource(id = R.string.vehicle_class),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.fillMaxWidth()

        )
        Text(
            text = vehicle.vehicle_class.lowercase(),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
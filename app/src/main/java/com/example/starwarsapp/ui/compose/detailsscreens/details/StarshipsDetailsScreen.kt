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
import com.example.starwarsapp.data.models.Starship
import com.example.starwarsapp.domain.utils.TestingTags
import com.example.starwarsapp.ui.compose.components.ContentFadeIn
import com.example.starwarsapp.ui.compose.detailsscreens.items.FilmsItem
import com.example.starwarsapp.ui.compose.detailsscreens.items.PeopleItem
import com.example.starwarsapp.ui.compose.components.FloatingButtons
import com.example.starwarsapp.ui.compose.components.HeaderExpander
import com.example.starwarsapp.ui.compose.components.LoadingOverlay
import com.example.starwarsapp.ui.viewmodels.detailsviewmodels.StarshipsDetailsViewModel

@Composable
fun StarshipsDetailsScreen(
    url: String,
    navController: NavController,
    starshipsDetailsViewModel: StarshipsDetailsViewModel = hiltViewModel()
) {

    val films = starshipsDetailsViewModel.films.collectAsState().value
    val people = starshipsDetailsViewModel.people.collectAsState().value
    val starship = starshipsDetailsViewModel.starship.collectAsState().value
    val isAddedAsFavorite = starshipsDetailsViewModel.isAddedAsFavorite.collectAsState().value
    val pendingRequests = starshipsDetailsViewModel.pendingRequests.collectAsState().value
    val error = starshipsDetailsViewModel.error.collectAsState().value
    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = url) {
        starshipsDetailsViewModel.getSpeciesById(url)
    }

    if(error.isNotEmpty()){
        LaunchedEffect(error) {
            snackBarHostState.showSnackbar(message = error)
        }
    }

    Scaffold(
        topBar = {
            HeaderExpander(isVisible = pendingRequests.isEmpty()) {
                StarshipsDetailsTopBar(starship = starship)
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
                        StarshipsDetailsContent(
                            starship = starship,
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
                            starshipsDetailsViewModel.addToFavorites()
                        },
                        removeFromFavorites = {
                            starshipsDetailsViewModel.removeFromFavorites()
                        }
                    )
                }
            }

        }

    )
}

@Composable
fun StarshipsDetailsTopBar(starship: Starship){
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
                text = starship.name.lowercase(),
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
fun StarshipsDetailsContent(
    starship: Starship,
    people: List<People>,
    films: List<Film>,
    navController: NavController
){
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .testTag(TestingTags.STARSHIPS_LIST)
    ) {
        item {
            StarshipContent(starship)
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
fun StarshipContent(starship: Starship) {
    Column {
        Text(
            text = stringResource(id = R.string.model),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier
                .fillMaxWidth()

        )
        Text(
            text = starship.model.lowercase(),
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
            text = starship.manufacturer.lowercase(),
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
            text = starship.cost_in_credits.lowercase(),
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
            text = starship.length.lowercase(),
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
            text = starship.max_atmosphering_speed.lowercase(),
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
            text = starship.crew.lowercase(),
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
            text = starship.crew.lowercase(),
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
            text = starship.passengers.lowercase(),
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
            text = starship.cargo_capacity.lowercase(),
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
            text = starship.consumables.lowercase(),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = stringResource(id = R.string.hyper_drive_rating),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.fillMaxWidth()

        )
        Text(
            text = starship.hyperdrive_rating.lowercase(),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = stringResource(id = R.string.MGLT),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.fillMaxWidth()

        )
        Text(
            text = starship.MGLT.lowercase(),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = stringResource(id = R.string.starship_class),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.fillMaxWidth()

        )
        Text(
            text = starship.starship_class.lowercase(),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier.fillMaxWidth()
        )
    }

}
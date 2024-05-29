package com.example.starwarsapp.ui.compose.listscreens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.starwarsapp.ui.compose.components.LoadingOverlay
import com.example.starwarsapp.ui.compose.navigation.Screen
import com.example.starwarsapp.ui.viewmodels.listviewmodels.PlanetListViewModel

@Composable
fun PlanetsScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    showSnackBar: (message: String) -> Unit,
    planetListViewModel: PlanetListViewModel = hiltViewModel()
) {

    val planets = planetListViewModel.planets.collectAsState().value
    val isLoading = planetListViewModel.isLoading.collectAsState().value
    val hasMore = planetListViewModel.hasMore.collectAsState().value
    val error = planetListViewModel.error.collectAsState().value

    if(error.isNotEmpty()){
        LaunchedEffect(error) {
            showSnackBar(error)
        }
    }

    LoadingOverlay(isLoading = isLoading) {
        LazyColumn(modifier = modifier.background(color = MaterialTheme.colorScheme.background)) {
            items(planets) { planet ->
                Column(
                    Modifier
                        .clickable {
                            navController.navigate(Screen.PlanetDetails.createRoute(planet.url))
                        }
                ) {

                    Text(
                        text = planet.name.lowercase(),
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

            if (hasMore && isLoading.not() && planets.isNotEmpty()) {
                item {
                    LaunchedEffect(planets.size) {
                        planetListViewModel.getAllPlanets()
                    }
                }
            }
        }
    }
}
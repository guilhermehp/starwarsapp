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
import com.example.starwarsapp.ui.viewmodels.listviewmodels.VehicleListViewModel

@Composable
fun VehiclesScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    showSnackBar: (message: String) -> Unit,
    vehiclesViewModel: VehicleListViewModel = hiltViewModel()
) {
    val vehicles = vehiclesViewModel.vehicles.collectAsState().value
    val isLoading = vehiclesViewModel.isLoading.collectAsState().value
    val hasMore = vehiclesViewModel.hasMore.collectAsState().value
    val error = vehiclesViewModel.error.collectAsState().value

    if(error.isNotEmpty()){
        LaunchedEffect(error) {
            showSnackBar(error)
        }
    }

    LoadingOverlay(isLoading = isLoading) {
        LazyColumn(modifier = modifier.background(color = MaterialTheme.colorScheme.background)) {
            items(vehicles) { vehicle ->
                Column(
                    Modifier
                        .clickable {
                            navController.navigate(Screen.VehiclesDetails.createRoute(vehicle.url))
                        }
                ) {

                    Text(
                        text = vehicle.name.lowercase(),
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

            if (hasMore && isLoading.not() && vehicles.isNotEmpty()) {
                item {
                    LaunchedEffect(vehicles.size) {
                        vehiclesViewModel.getAllVehicles()
                    }
                }
            }
        }
    }
}
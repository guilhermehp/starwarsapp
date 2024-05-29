package com.example.starwarsapp.ui.compose.listscreens

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.starwarsapp.ui.compose.components.LoadingOverlay
import com.example.starwarsapp.ui.compose.navigation.Screen
import com.example.starwarsapp.ui.viewmodels.listviewmodels.FilmListViewModel

@Composable
fun FilmsScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    showSnackBar: (message: String) -> Unit,
    filmListViewModel: FilmListViewModel = hiltViewModel(),
) {
    val films = filmListViewModel.films.collectAsState().value
    val isLoading = filmListViewModel.isLoading.collectAsState().value
    val hasMore = filmListViewModel.hasMore.collectAsState().value
    val error = filmListViewModel.error.collectAsState().value

    if (error.isNotEmpty()) {
        LaunchedEffect(error) {
            showSnackBar(error)
        }
    }

    LoadingOverlay(isLoading = isLoading) {
        LazyColumn(modifier = modifier.background(color = MaterialTheme.colorScheme.background)) {
            items(films) { film ->

                Column(
                    Modifier
                        .clickable {
                            navController.navigate(Screen.FilmsDetails.createRoute(film.url))
                        }
                ) {

                    Text(
                        text = film.title.lowercase(),
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


            if (hasMore && isLoading.not() && films.isNotEmpty()) {
                item {
                    LaunchedEffect(films.size) {
                        filmListViewModel.getAllFilms()
                    }
                }
            }
        }
    }
}
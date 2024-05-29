package com.example.starwarsapp.ui.compose.detailsscreens.items

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.starwarsapp.R
import com.example.starwarsapp.data.models.Film
import com.example.starwarsapp.ui.compose.navigation.Screen

@Composable()
fun FilmsItem(films: List<Film>, navController: NavController) {

    if (films.isEmpty().not()) {
        Column {
            Text(
                text = stringResource(id = R.string.films),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.fillMaxWidth()
            )
            films.forEach { film ->
                Text(
                    text = film.title.lowercase(),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.inversePrimary,
                    modifier = Modifier.fillMaxWidth().clickable {
                        navController.navigate(Screen.FilmsDetails.createRoute(film.url))
                    }
                )
            }
        }
    }
}
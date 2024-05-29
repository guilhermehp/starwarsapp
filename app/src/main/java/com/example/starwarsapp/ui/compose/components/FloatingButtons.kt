package com.example.starwarsapp.ui.compose.components

import androidx.compose.foundation.background
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.navigation.NavController
import com.example.starwarsapp.domain.utils.TestingTags

@Composable
fun FloatingButtons(
    navController: NavController,
    isAddedAsFavorite: Boolean,
    addToFavorites: () -> Unit,
    removeFromFavorites: () -> Unit
) {
    FloatingActionButton(
        onClick = {
            if (isAddedAsFavorite) {
                removeFromFavorites()
            } else {
                addToFavorites()
            }
        },
        modifier = Modifier.background(color = MaterialTheme.colorScheme.background),
        containerColor = MaterialTheme.colorScheme.primary
    ) {
        if (isAddedAsFavorite) {
            Icon(
                Icons.Default.Favorite,
                contentDescription = "Remove From Favorites",
                tint = MaterialTheme.colorScheme.secondary,
            )
        } else {
            Icon(
                Icons.Default.FavoriteBorder,
                contentDescription = "Add to Favorites",
                tint = MaterialTheme.colorScheme.secondary,
            )
        }
    }
    FloatingActionButton(
        onClick = { navController.popBackStack("home", false) },
        modifier = Modifier.background(color = MaterialTheme.colorScheme.background).testTag(TestingTags.HOME_BUTTON),
        containerColor = MaterialTheme.colorScheme.primary
    ) {
        Icon(
            Icons.Default.Home,
            contentDescription = "Go to Home Screen",
            tint = MaterialTheme.colorScheme.secondary,
        )
    }
}
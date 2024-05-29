package com.example.starwarsapp.ui.compose.detailsscreens.items

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
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
import com.example.starwarsapp.data.models.People
import com.example.starwarsapp.ui.compose.navigation.Screen

@Composable()
fun PeopleItem(people: List<People>, isPlanet: Boolean = false, isPilot: Boolean = false, navController: NavController) {
    if (people.isEmpty().not()) {
        Column {
            Text(
                text = stringResource(id = if(isPlanet) R.string.residents else if (isPilot) R.string.pilots else R.string.characters),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.fillMaxWidth()
            )
            people.forEach { people ->
                Text(
                    text = people.name.lowercase(),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.inversePrimary,
                    modifier = Modifier.fillMaxWidth().clickable {
                        navController.navigate(Screen.PeopleDetails.createRoute(people.url))
                    }
                )
            }
        }
    }
}
package com.example.starwarsapp.ui.compose.listscreens

import android.util.Log
import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.starwarsapp.ui.compose.components.LoadingOverlay
import com.example.starwarsapp.ui.compose.navigation.Screen
import com.example.starwarsapp.ui.viewmodels.listviewmodels.PeopleListViewModel

@Composable
fun PeopleScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    showSnackBar: (message: String) -> Unit,
    peopleListViewModel: PeopleListViewModel = hiltViewModel(),
    ) {
    val people = peopleListViewModel.people.collectAsState().value
    val isLoading = peopleListViewModel.isLoading.collectAsState().value
    val hasMore = peopleListViewModel.hasMore.collectAsState().value
    val error = peopleListViewModel.error.collectAsState().value

    if(error.isNotEmpty()){
        LaunchedEffect(error) {
           showSnackBar(error)
        }
    }

    LoadingOverlay(isLoading = isLoading) {
        LazyColumn(
            modifier = modifier
                .background(color = MaterialTheme.colorScheme.background)
                .testTag("PeopleList")
        ) {
            items(people) { person ->
                Column(
                    Modifier
                        .clickable {
                            navController.navigate(Screen.PeopleDetails.createRoute(person.url))
                        }
                ) {

                    Text(
                        text = person.name.lowercase(),
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.tertiary,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .testTag("PeopleListItem")
                    )
                    HorizontalDivider(
                        thickness = 0.50.dp,
                        color = MaterialTheme.colorScheme.tertiary
                    )
                }
            }

            if (hasMore && isLoading.not() && people.isNotEmpty()) {
                item {
                    LaunchedEffect(people.size) {
                        peopleListViewModel.getAllPeople()
                    }
                }
            }
        }
    }
}
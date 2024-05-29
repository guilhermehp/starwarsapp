package com.example.starwarsapp.ui.compose.navigation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOut
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.animation.with
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.starwarsapp.R
import com.example.starwarsapp.domain.utils.TestingTags
import com.example.starwarsapp.ui.compose.listscreens.FilmsScreen
import com.example.starwarsapp.ui.compose.listscreens.PeopleScreen
import com.example.starwarsapp.ui.compose.listscreens.PlanetsScreen
import com.example.starwarsapp.ui.compose.listscreens.SpeciesScreen
import com.example.starwarsapp.ui.compose.listscreens.StarshipsScreen
import com.example.starwarsapp.ui.compose.listscreens.VehiclesScreen
import com.example.starwarsapp.ui.viewmodels.HomeViewModel
import com.example.starwarsapp.ui.viewmodels.MainViewModel
import dagger.hilt.android.lifecycle.HiltViewModel

@Composable
fun HomeScreen(
    navController: NavController
) {
    var selectedTabIndex by rememberSaveable { mutableIntStateOf(0) }
    val snackBarError = remember { mutableStateOf("") }
    val snackBarHostState = remember { SnackbarHostState() }

    if (snackBarError.value.isNotEmpty()) {
        LaunchedEffect(snackBarError) {
            snackBarHostState.showSnackbar(message = snackBarError.value)
        }
    }

    Scaffold(
        topBar = {
            Column(modifier = Modifier.background(color = MaterialTheme.colorScheme.primary)) {
                TopAppBarContent(navController)
                ScrollableTabRowContent(
                    selectedTabIndex = selectedTabIndex,
                    onTabSelected = { index -> selectedTabIndex = index }
                )
            }
        },
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        }
    ) { innerPadding ->
        val modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
        AnimatedContent(
            targetState = selectedTabIndex,
            transitionSpec = {
                if (targetState > initialState) {
                    slideInHorizontally(animationSpec = tween(durationMillis = 500)) { width -> width } + fadeIn(
                        animationSpec = tween(durationMillis = 1000)
                    ) togetherWith
                            slideOutHorizontally(animationSpec = tween(durationMillis = 500)) { width -> -width } + fadeOut(
                        animationSpec = tween(durationMillis = 1000)
                    )
                } else {
                    slideInHorizontally(animationSpec = tween(durationMillis = 500)) { width -> -width } + fadeIn(
                        animationSpec = tween(durationMillis = 1000)
                    ) togetherWith
                            slideOutHorizontally(animationSpec = tween(durationMillis = 500)) { width -> width } + fadeOut(
                        animationSpec = tween(durationMillis = 1000)
                    )
                }.using(
                    SizeTransform(clip = false)
                )
            }, label = ""
        ) { target ->
            when (target) {
                0 -> PeopleScreen(modifier, navController, { error ->
                    snackBarError.value = error
                })

                1 -> FilmsScreen(modifier, navController, { error ->
                    snackBarError.value = error
                })

                2 -> PlanetsScreen(modifier, navController, { error ->
                    snackBarError.value = error
                })

                3 -> SpeciesScreen(modifier, navController, { error ->
                    snackBarError.value = error
                })

                4 -> StarshipsScreen(modifier, navController, { error ->
                    snackBarError.value = error
                })

                5 -> VehiclesScreen(modifier, navController, { error ->
                    snackBarError.value = error
                })
            }
        }
    }
}

@Composable
fun TopAppBarContent(navController: NavController, homeViewModel: HomeViewModel = hiltViewModel()) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,

        ) {
        IconButton(onClick = {
            homeViewModel.changeTheme()
        }) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "Change Theme",
                modifier = Modifier.size(24.dp),
                tint = MaterialTheme.colorScheme.secondary
            )
        }
        Text(
            text = "STAR WARS",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.secondary
        )
        IconButton(
            onClick = { navController.navigate(Screen.Favorites.route) },
            modifier = Modifier.testTag(TestingTags.FAVORITES_BUTTON)) {
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = "Favorites",
                modifier = Modifier.size(24.dp),
                tint = MaterialTheme.colorScheme.secondary
            )
        }
    }
}

@Composable
fun ScrollableTabRowContent(selectedTabIndex: Int, onTabSelected: (Int) -> Unit) {
    val tabs = listOf(
        stringResource(id = R.string.people),
        stringResource(id = R.string.films),
        stringResource(id = R.string.planets),
        stringResource(id = R.string.species),
        stringResource(id = R.string.starships),
        stringResource(id = R.string.vehicles)
    )

    ScrollableTabRow(
        selectedTabIndex = selectedTabIndex,
        Modifier
            .background(
                color = MaterialTheme
                    .colorScheme.primary
            ), edgePadding = 0.dp,
        divider = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(2.dp)
                    .background(MaterialTheme.colorScheme.secondary)
            )
        }
    ) {
        tabs.forEachIndexed { index, title ->
            Tab(
                text = {
                    Text(
                        text = title,
                        fontSize = 14.sp,
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.secondary
                    )
                },
                selected = selectedTabIndex == index,
                onClick = { onTabSelected(index) },
                modifier = Modifier.background(color = MaterialTheme.colorScheme.primary)
            )
        }
    }

}
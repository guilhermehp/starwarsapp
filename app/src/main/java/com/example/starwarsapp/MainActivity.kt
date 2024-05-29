package com.example.starwarsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.starwarsapp.ui.compose.navigation.AppNavigation
import com.example.starwarsapp.ui.theme.StarWarsAppTheme
import com.example.starwarsapp.ui.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent()
    }

    private fun setContent(){
        setContent {
            val mainViewModel: MainViewModel = hiltViewModel()
            val theme = mainViewModel.theme.collectAsState().value

            LaunchedEffect(key1 = Unit) {
                mainViewModel.getCurrentTheme()
            }

            if(theme.isEmpty().not()){
                StarWarsAppTheme(theme) {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        AppNavigation()
                    }
                }
            }
        }
    }
}
package com.example.starwarsapp.ui.viewmodels.listviewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.starwarsapp.data.models.ApiResponse
import com.example.starwarsapp.data.models.Favorite
import com.example.starwarsapp.data.models.FavoriteType
import com.example.starwarsapp.data.models.Film
import com.example.starwarsapp.domain.usecases.FavoritesUseCase
import com.example.starwarsapp.ui.compose.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val favoritesUseCase: FavoritesUseCase
) : ViewModel() {

    private val _favorites = MutableStateFlow<List<Favorite>>(emptyList())
    val favorites: StateFlow<List<Favorite>> = _favorites
    private var originalList : List<Favorite> = emptyList()

    fun getAllFavorites(){
        viewModelScope.launch {
            favoritesUseCase.getAllFavorites()
                .collect {  favorites ->
                    _favorites.value = favorites.reversed()
                    originalList = favorites.reversed()
                }
        }
    }

    fun filterPeople(){
        _favorites.value = originalList.filter { it.type == FavoriteType.PEOPLE }
    }

    fun filterFilms(){
        _favorites.value = originalList.filter { it.type == FavoriteType.FILM }
    }

    fun filterPlanets(){
        _favorites.value = originalList.filter { it.type == FavoriteType.PLANET }
    }

    fun filterSpecies(){
        _favorites.value = originalList.filter { it.type == FavoriteType.SPECIES }
    }

    fun filterStarships(){
        _favorites.value = originalList.filter { it.type == FavoriteType.STARSHIP }
    }

    fun filterVehicles(){
        _favorites.value = originalList.filter { it.type == FavoriteType.VEHICLE }
    }

    fun filterReverse(){
        _favorites.value = _favorites.value.reversed()
    }

    fun clearFilter(){
        _favorites.value = originalList
    }

}
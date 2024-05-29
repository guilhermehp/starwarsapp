package com.example.starwarsapp.ui.viewmodels.detailsviewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.starwarsapp.data.models.ApiResponse
import com.example.starwarsapp.data.models.Film
import com.example.starwarsapp.data.models.People
import com.example.starwarsapp.data.models.Planet
import com.example.starwarsapp.data.models.Species
import com.example.starwarsapp.domain.usecases.FavoritesUseCase
import com.example.starwarsapp.domain.usecases.FilmsUseCase
import com.example.starwarsapp.domain.usecases.PeopleUseCase
import com.example.starwarsapp.domain.usecases.PlanetsUseCase
import com.example.starwarsapp.domain.usecases.SpeciesUseCase
import com.example.starwarsapp.domain.utils.AnimationConfigInterface
import com.example.starwarsapp.domain.utils.LoadingStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SpeciesDetailsViewModel @Inject constructor(
    private val peopleUseCase: PeopleUseCase,
    private val filmsUseCase: FilmsUseCase,
    private val planetsUseCase: PlanetsUseCase,
    private val speciesUseCase: SpeciesUseCase,
    private val favoritesUseCase: FavoritesUseCase,
    private val animationConfig: AnimationConfigInterface
) : ViewModel() {

    private val _species = MutableStateFlow<Species>(Species())
    val species: StateFlow<Species> = _species

    private val _films = MutableStateFlow<List<Film>>(emptyList())
    val films: StateFlow<List<Film>> = _films

    private val _people = MutableStateFlow<List<People>>(emptyList())
    val people: StateFlow<List<People>> = _people

    private val _planet = MutableStateFlow(Planet())
    val planet: StateFlow<Planet> = _planet

    private val _pendingRequests = MutableStateFlow<List<LoadingStates>>(listOf(LoadingStates.LOADING_DELAY))
    val pendingRequests: StateFlow<List<LoadingStates>> get() = _pendingRequests

    private val _isAddedAsFavorite = MutableStateFlow(false)
    val isAddedAsFavorite: StateFlow<Boolean> get() = _isAddedAsFavorite

    private val _error = MutableStateFlow("")
    val error: StateFlow<String> get() = _error

    init {
        viewModelScope.launch {
            if(animationConfig.isAnimationEnabled){
                delay(500)
            }
            removePendingRequest(LoadingStates.LOADING_DELAY)
        }
    }

    fun getSpeciesById(url: String) {
        viewModelScope.launch {
            speciesUseCase.getSpeciesById(url)
                .catch { e ->
                    emit(ApiResponse.Error(e.message))
                }
                .collect { apiResponse ->
                    when (apiResponse) {
                        is ApiResponse.Error -> {
                            removePendingRequest(LoadingStates.LOADING_SPECIES)
                            _error.value = apiResponse.error ?: ""
                        }

                        is ApiResponse.Loading -> {
                            addPendingRequest(LoadingStates.LOADING_SPECIES)
                        }

                        is ApiResponse.Success -> {
                            removePendingRequest(LoadingStates.LOADING_SPECIES)
                            _species.value = apiResponse.data
                            checkFavorite()
                            getAllPeopleByIds(apiResponse.data.people)
                            getAllFilmsByIds(apiResponse.data.films)
                            if(apiResponse.data.homeworld.isNullOrEmpty().not()){
                                getPlanetById(apiResponse.data.homeworld)
                            }
                        }
                    }
                }
        }
    }

    private fun getPlanetById(url: String) {
        viewModelScope.launch {
            planetsUseCase.getPlanetById(url)
                .catch { e ->
                    emit(ApiResponse.Error(e.message))
                }
                .collect { apiResponse ->
                    when (apiResponse) {
                        is ApiResponse.Error -> {
                            removePendingRequest(LoadingStates.LOADING_PLANETS)
                            _error.value = apiResponse.error ?: ""
                        }

                        is ApiResponse.Loading -> {
                            addPendingRequest(LoadingStates.LOADING_PLANETS)
                        }

                        is ApiResponse.Success -> {
                            removePendingRequest(LoadingStates.LOADING_PLANETS)
                            _planet.value = apiResponse.data
                        }
                    }
                }
        }
    }

    private fun getAllPeopleByIds(urlList: List<String>) {
        viewModelScope.launch {
            peopleUseCase.getAllPeopleByIds(urlList)
                .catch { e ->
                    emit(ApiResponse.Error(e.message))
                }
                .collect { apiResponse ->
                    when (apiResponse) {
                        is ApiResponse.Error -> {
                            removePendingRequest(LoadingStates.LOADING_PEOPLE)
                            _error.value = apiResponse.error ?: ""
                        }
                        is ApiResponse.Loading -> {
                            addPendingRequest(LoadingStates.LOADING_PEOPLE)
                        }
                        is ApiResponse.Success -> {
                            removePendingRequest(LoadingStates.LOADING_PEOPLE)
                            _people.value = apiResponse.data
                        }
                    }
                }
        }
    }

    private fun getAllFilmsByIds(urlList: List<String>) {
        viewModelScope.launch {
            filmsUseCase.getAllFilmsByIds(urlList)
                .catch { e ->
                    emit(ApiResponse.Error(e.message))
                }
                .collect { apiResponse ->
                    when (apiResponse) {
                        is ApiResponse.Error -> {
                            removePendingRequest(LoadingStates.LOADING_FILMS)
                            _error.value = apiResponse.error ?: ""
                        }
                        is ApiResponse.Loading -> {
                            addPendingRequest(LoadingStates.LOADING_FILMS)
                        }
                        is ApiResponse.Success -> {
                            removePendingRequest(LoadingStates.LOADING_FILMS)
                            _films.value = apiResponse.data
                        }
                    }
                }
        }
    }

    private fun checkFavorite(){
        viewModelScope.launch {
            favoritesUseCase.getFavoriteByUrl(_species.value.url).collect { favorite ->
                _isAddedAsFavorite.value = favorite != null
            }
        }
    }

    fun addToFavorites(){
        addPendingRequest(LoadingStates.LOADING_FAVORITES)
        viewModelScope.launch {
            favoritesUseCase.insertSpecies(_species.value)
            _isAddedAsFavorite.value = true
            removePendingRequest(LoadingStates.LOADING_FAVORITES)
        }
    }

    fun removeFromFavorites(){
        viewModelScope.launch {
            favoritesUseCase.deleteSpecies(_species.value)
            _isAddedAsFavorite.value = false
        }
    }

    private fun addPendingRequest(loadingStates: LoadingStates){
        if(_pendingRequests.value.contains(loadingStates).not()){
            _pendingRequests.value += loadingStates
        }
    }

    private fun removePendingRequest(loadingStates: LoadingStates){
        _pendingRequests.value -= loadingStates
    }

}
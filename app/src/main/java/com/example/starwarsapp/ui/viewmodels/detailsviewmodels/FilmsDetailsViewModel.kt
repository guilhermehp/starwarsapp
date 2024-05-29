package com.example.starwarsapp.ui.viewmodels.detailsviewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.starwarsapp.data.models.ApiResponse
import com.example.starwarsapp.data.models.Film
import com.example.starwarsapp.data.models.People
import com.example.starwarsapp.data.models.Planet
import com.example.starwarsapp.data.models.Species
import com.example.starwarsapp.data.models.Starship
import com.example.starwarsapp.data.models.Vehicle
import com.example.starwarsapp.domain.usecases.FavoritesUseCase
import com.example.starwarsapp.domain.usecases.FilmsUseCase
import com.example.starwarsapp.domain.usecases.PeopleUseCase
import com.example.starwarsapp.domain.usecases.PlanetsUseCase
import com.example.starwarsapp.domain.usecases.SpeciesUseCase
import com.example.starwarsapp.domain.usecases.StarshipsUseCase
import com.example.starwarsapp.domain.usecases.VehiclesUseCase
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
class FilmsDetailsViewModel @Inject constructor(
    private val peopleUseCase: PeopleUseCase,
    private val planetsUseCase: PlanetsUseCase,
    private val filmsUseCase: FilmsUseCase,
    private val vehiclesUseCase: VehiclesUseCase,
    private val speciesUseCase: SpeciesUseCase,
    private val starshipsUseCase: StarshipsUseCase,
    private val favoritesUseCase: FavoritesUseCase,
    private val animationConfig: AnimationConfigInterface
) : ViewModel() {

    private val _film = MutableStateFlow(Film())
    val film: StateFlow<Film> = _film

    private val _people = MutableStateFlow<List<People>>(emptyList())
    val people: StateFlow<List<People>> = _people

    private val _planets = MutableStateFlow<List<Planet>>(emptyList())
    val planets: StateFlow<List<Planet>> = _planets

    private val _species = MutableStateFlow<List<Species>>(emptyList())
    val species: StateFlow<List<Species>> = _species

    private val _starships = MutableStateFlow<List<Starship>>(emptyList())
    val starships: StateFlow<List<Starship>> = _starships

    private val _vehicles = MutableStateFlow<List<Vehicle>>(emptyList())
    val vehicles: StateFlow<List<Vehicle>> = _vehicles

    private val _pendingRequests = MutableStateFlow(listOf(LoadingStates.LOADING_DELAY))
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

    fun getFilmById(url: String) {
        viewModelScope.launch {
            filmsUseCase.getFilmById(url)
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
                            _film.value = apiResponse.data
                            checkFavorite()
                            getAllPeopleByIds(apiResponse.data.characters)
                            getAllPlanetsById(apiResponse.data.planets)
                            getAllVehiclesByIds(apiResponse.data.vehicles)
                            getAllStarshipsByIds(apiResponse.data.starships)
                            getAllSpeciesByIds(apiResponse.data.species)
                        }
                    }
                }
        }
    }

    private fun getAllPlanetsById(urlList: List<String>) {
        viewModelScope.launch {
            planetsUseCase.getAllPlanetsById(urlList)
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
                            _planets.value = apiResponse.data
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

    private fun getAllSpeciesByIds(urlList: List<String>) {
        viewModelScope.launch {
            speciesUseCase.getAllSpeciesByIds(urlList)
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
                        }
                    }
                }
        }
    }

    private fun getAllVehiclesByIds(urlList: List<String>) {
        viewModelScope.launch {
            vehiclesUseCase.getAllVehiclesByIds(urlList)
                .catch { e ->
                    emit(ApiResponse.Error(e.message))
                }
                .collect { apiResponse ->
                    when (apiResponse) {
                        is ApiResponse.Error -> {
                            removePendingRequest(LoadingStates.LOADING_VEHICLES)
                            _error.value = apiResponse.error ?: ""
                        }
                        is ApiResponse.Loading -> {
                            addPendingRequest(LoadingStates.LOADING_VEHICLES)
                        }
                        is ApiResponse.Success -> {
                            removePendingRequest(LoadingStates.LOADING_VEHICLES)
                            _vehicles.value = apiResponse.data
                        }
                    }
                }
        }
    }

    private fun getAllStarshipsByIds(urlList: List<String>) {
        viewModelScope.launch {
            starshipsUseCase.getAllStarshipsByIds(urlList)
                .catch { e ->
                    emit(ApiResponse.Error(e.message))
                }
                .collect { apiResponse ->
                    when (apiResponse) {
                        is ApiResponse.Error -> {
                            removePendingRequest(LoadingStates.LOADING_STARSHIPS)
                            _error.value = apiResponse.error ?: ""
                        }
                        is ApiResponse.Loading -> {
                            addPendingRequest(LoadingStates.LOADING_STARSHIPS)
                        }
                        is ApiResponse.Success -> {
                            removePendingRequest(LoadingStates.LOADING_STARSHIPS)
                            _starships.value = apiResponse.data
                        }
                    }
                }
        }
    }

    private fun checkFavorite(){
        addPendingRequest(LoadingStates.LOADING_FAVORITES)
        viewModelScope.launch {
            favoritesUseCase.getFavoriteByUrl(_film.value.url).collect { favorite ->
                _isAddedAsFavorite.value = favorite != null
                removePendingRequest(LoadingStates.LOADING_FAVORITES)
            }
        }
    }

    fun addToFavorites(){
        viewModelScope.launch {
            favoritesUseCase.insertFilm(_film.value)
            _isAddedAsFavorite.value = true
        }
    }

    fun removeFromFavorites(){
        viewModelScope.launch {
            favoritesUseCase.deleteFilm(_film.value)
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
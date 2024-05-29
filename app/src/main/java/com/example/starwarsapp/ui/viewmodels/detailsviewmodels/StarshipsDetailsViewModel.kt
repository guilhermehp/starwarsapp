package com.example.starwarsapp.ui.viewmodels.detailsviewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.starwarsapp.data.models.ApiResponse
import com.example.starwarsapp.data.models.Film
import com.example.starwarsapp.data.models.People
import com.example.starwarsapp.data.models.Starship
import com.example.starwarsapp.domain.usecases.FavoritesUseCase
import com.example.starwarsapp.domain.usecases.FilmsUseCase
import com.example.starwarsapp.domain.usecases.PeopleUseCase
import com.example.starwarsapp.domain.usecases.StarshipsUseCase
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
class StarshipsDetailsViewModel @Inject constructor(
    private val peopleUseCase: PeopleUseCase,
    private val filmsUseCase: FilmsUseCase,
    private val starshipsUseCase: StarshipsUseCase,
    private val favoritesUseCase: FavoritesUseCase,
    private val animationConfig: AnimationConfigInterface
) : ViewModel() {

    private val _starship = MutableStateFlow(Starship())
    val starship: StateFlow<Starship> = _starship

    private val _films = MutableStateFlow<List<Film>>(emptyList())
    val films: StateFlow<List<Film>> = _films

    private val _people = MutableStateFlow<List<People>>(emptyList())
    val people: StateFlow<List<People>> = _people

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
            starshipsUseCase.getStarshipById(url)
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
                            _starship.value = apiResponse.data
                            checkFavorite()
                            getAllPeopleByIds(apiResponse.data.pilots)
                            getAllFilmsByIds(apiResponse.data.films)
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
            favoritesUseCase.getFavoriteByUrl(_starship.value.url).collect { favorite ->
                _isAddedAsFavorite.value = favorite != null
            }
        }
    }

    fun addToFavorites(){
        viewModelScope.launch {
            favoritesUseCase.insertStarship(_starship.value)
            _isAddedAsFavorite.value = true
        }
    }

    fun removeFromFavorites(){
        addPendingRequest(LoadingStates.LOADING_FAVORITES)
        viewModelScope.launch {
            favoritesUseCase.deleteStarship(_starship.value)
            _isAddedAsFavorite.value = false
            removePendingRequest(LoadingStates.LOADING_FAVORITES)
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
package com.example.starwarsapp.ui.viewmodels.listviewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.starwarsapp.data.models.ApiResponse
import com.example.starwarsapp.data.models.Film
import com.example.starwarsapp.domain.usecases.FilmsUseCase
import com.example.starwarsapp.domain.utils.AnimationConfigInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FilmListViewModel @Inject constructor(
    private val filmsUseCase: FilmsUseCase,
    private val animationConfigInterface: AnimationConfigInterface
): ViewModel() {

    private var nextUrl: String? = null

    private val _films = MutableStateFlow<List<Film>>(emptyList())
    val films: StateFlow<List<Film>> = _films

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _hasMore = MutableStateFlow(true)
    val hasMore: StateFlow<Boolean> get() = _hasMore

    private val _error = MutableStateFlow("")
    val error: StateFlow<String> get() = _error

    init {
        getAllFilms()
    }

    fun getAllFilms(){
        viewModelScope.launch {
            filmsUseCase.getAllFilms(nextUrl)
                .catch { e ->
                    emit(ApiResponse.Error(e.message))
                }
                .collect { apiResponse ->
                when (apiResponse) {
                    is ApiResponse.Error -> {
                        _isLoading.value = false
                        _error.value = apiResponse.error ?: ""
                    }
                    is ApiResponse.Loading -> {
                        _isLoading.value = true
                    }
                    is ApiResponse.Success -> {
                        if(animationConfigInterface.isAnimationEnabled){
                            delay(500)
                        }
                        _isLoading.value = false
                        _films.value += apiResponse.data.results

                        if (apiResponse.data.next.isNullOrEmpty().not()){
                            nextUrl = apiResponse.data.next
                        } else {
                            _hasMore.value = false
                        }
                    }
                }
            }
        }
    }


}
package com.example.starwarsapp.ui.viewmodels.listviewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.starwarsapp.data.models.ApiResponse
import com.example.starwarsapp.data.models.Species
import com.example.starwarsapp.domain.usecases.SpeciesUseCase
import com.example.starwarsapp.domain.utils.AnimationConfigInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SpeciesListViewModel @Inject constructor(
    private val speciesUseCase: SpeciesUseCase,
    private val animationConfig: AnimationConfigInterface
): ViewModel() {

    private var nextUrl: String? = null

    private val _species = MutableStateFlow<List<Species>>(emptyList())
    val species: StateFlow<List<Species>> = _species

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _hasMore = MutableStateFlow(true)
    val hasMore: StateFlow<Boolean> get() = _hasMore

    private val _error = MutableStateFlow("")
    val error: StateFlow<String> get() = _error

    init {
        getAllSpecies()
    }

    fun getAllSpecies(){
        viewModelScope.launch {
            speciesUseCase.getAllSpecies(nextUrl)
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
                        if(animationConfig.isAnimationEnabled){
                            delay(500)
                        }
                        _isLoading.value = false
                        _species.value += apiResponse.data.results

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

    fun clearList(){
        _species.value = emptyList()
    }

}
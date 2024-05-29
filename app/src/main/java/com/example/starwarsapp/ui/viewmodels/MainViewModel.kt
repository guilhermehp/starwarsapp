package com.example.starwarsapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.starwarsapp.domain.usecases.ThemesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val themesUseCase: ThemesUseCase
) : ViewModel() {

    private val _theme = MutableStateFlow("")
    val theme: StateFlow<String> = _theme

    fun getCurrentTheme() {
        viewModelScope.launch {
            themesUseCase.getCurrentTheme().collect { response ->
                response?.let {
                    _theme.value = response
                }
            }
        }
    }

}
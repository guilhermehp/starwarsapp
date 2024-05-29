package com.example.starwarsapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.starwarsapp.domain.usecases.ThemesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val themesUseCase: ThemesUseCase
) : ViewModel() {

    private var theme = ""

    init {
        viewModelScope.launch {
            themesUseCase.getCurrentTheme().collect{response ->
                response?.let {
                    theme = response
                }
            }
        }
    }

    fun changeTheme(){
        viewModelScope.launch {
            themesUseCase.changeTheme(theme)
        }
    }

}
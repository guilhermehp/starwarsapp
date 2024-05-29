package com.example.starwarsapp.domain.usecases

import com.example.starwarsapp.data.repositories.StarshipsRepository
import com.example.starwarsapp.data.repositories.ThemesRepository
import com.example.starwarsapp.domain.utils.AppThemes
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ThemesUseCase @Inject constructor(private val themesRepository: ThemesRepository) {

    suspend fun changeTheme(theme: String) {
        when(theme){
            AppThemes.EMPIRE -> themesRepository.setTheme(AppThemes.REBELS)
            AppThemes.REBELS -> themesRepository.setTheme(AppThemes.SITH)
            AppThemes.SITH -> themesRepository.setTheme(AppThemes.JEDI)
            AppThemes.JEDI -> themesRepository.setTheme(AppThemes.EMPIRE)
            else -> themesRepository.setTheme(AppThemes.EMPIRE)
        }
    }

    fun getCurrentTheme(): Flow<String?> {
        return themesRepository.getCurrentTheme()
    }

}

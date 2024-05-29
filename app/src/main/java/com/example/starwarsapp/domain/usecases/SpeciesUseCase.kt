package com.example.starwarsapp.domain.usecases

import com.example.starwarsapp.data.models.ApiResponse
import com.example.starwarsapp.data.models.Film
import com.example.starwarsapp.data.models.Pagination
import com.example.starwarsapp.data.models.Species
import com.example.starwarsapp.data.repositories.SpeciesRepository
import com.example.starwarsapp.domain.utils.UrlUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class SpeciesUseCase @Inject constructor(private val speciesRepository: SpeciesRepository) {

    fun getAllSpecies(nextUrl: String?): Flow<ApiResponse<Pagination<Species>>> {
        return speciesRepository.getAllSpecies(UrlUtils.getPageNumberFromUrl(nextUrl ?: "0"))
    }

    fun getSpeciesById(url: String): Flow<ApiResponse<Species>> {
        return speciesRepository.getSpeciesById(UrlUtils.getIdFromUrl(url))
    }

    fun getAllSpeciesByIds(urlList: List<String>): Flow<ApiResponse<List<Species>>> {
        val flows = urlList.map { url ->
            speciesRepository.getSpeciesById(UrlUtils.getIdFromUrl(url))
        }
        return combine(flows) { responses ->
            val species = mutableListOf<Species>()
            responses.forEach {response ->
                when(response) {
                    is ApiResponse.Error -> {
                        return@combine ApiResponse.Error(response.error)
                    }
                    is ApiResponse.Loading -> {
                        return@combine ApiResponse.Loading(true)
                    }
                    is ApiResponse.Success -> {
                        species.add(response.data)
                    }
                }
            }
            return@combine ApiResponse.Success<List<Species>>(species)
        }
    }

}
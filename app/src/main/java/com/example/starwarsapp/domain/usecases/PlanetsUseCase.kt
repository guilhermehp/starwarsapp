package com.example.starwarsapp.domain.usecases

import com.example.starwarsapp.data.models.ApiResponse
import com.example.starwarsapp.data.models.Film
import com.example.starwarsapp.data.models.Pagination
import com.example.starwarsapp.data.models.Planet
import com.example.starwarsapp.data.repositories.PlanetsRepository
import com.example.starwarsapp.domain.utils.UrlUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class PlanetsUseCase @Inject constructor(private val planetsRepository: PlanetsRepository) {

    fun getAllPlanets(nextUrl: String?): Flow<ApiResponse<Pagination<Planet>>> {
        return planetsRepository.getAllPlanets(UrlUtils.getPageNumberFromUrl(nextUrl ?: "0"))
    }

    fun getPlanetById(url: String): Flow<ApiResponse<Planet>> {
        return planetsRepository.getPlanetById(UrlUtils.getIdFromUrl(url))
    }

    fun getAllPlanetsById(urlList: List<String>): Flow<ApiResponse<List<Planet>>> {
        val flows = urlList.map { url ->
            planetsRepository.getPlanetById(UrlUtils.getIdFromUrl(url))
        }
        return combine(flows) { responses ->
            val planets = mutableListOf<Planet>()
            responses.forEach {response ->
                when(response) {
                    is ApiResponse.Error -> {
                        return@combine ApiResponse.Error(response.error)
                    }
                    is ApiResponse.Loading -> {
                        return@combine ApiResponse.Loading(true)
                    }
                    is ApiResponse.Success -> {
                        planets.add(response.data)
                    }
                }
            }
            return@combine ApiResponse.Success<List<Planet>>(planets)
        }
    }

}
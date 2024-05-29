package com.example.starwarsapp.domain.usecases

import com.example.starwarsapp.data.models.ApiResponse
import com.example.starwarsapp.data.models.Pagination
import com.example.starwarsapp.data.models.Species
import com.example.starwarsapp.data.models.Starship
import com.example.starwarsapp.data.repositories.StarshipsRepository
import com.example.starwarsapp.domain.utils.UrlUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class StarshipsUseCase @Inject constructor(private val starshipsRepository: StarshipsRepository) {

    fun getAllStarships(nextUrl: String?): Flow<ApiResponse<Pagination<Starship>>> {
        return starshipsRepository.getAllStarships(UrlUtils.getPageNumberFromUrl(nextUrl ?: "0"))
    }

    fun getStarshipById(url: String): Flow<ApiResponse<Starship>> {
        return starshipsRepository.getStarShipById(UrlUtils.getIdFromUrl(url))
    }

    fun getAllStarshipsByIds(urlList: List<String>): Flow<ApiResponse<List<Starship>>> {
        val flows = urlList.map { url ->
            starshipsRepository.getStarShipById(UrlUtils.getIdFromUrl(url))
        }
        return combine(flows) { responses ->
            val starships = mutableListOf<Starship>()
            responses.forEach {response ->
                when(response) {
                    is ApiResponse.Error -> {
                        return@combine ApiResponse.Error(response.error)
                    }
                    is ApiResponse.Loading -> {
                        return@combine ApiResponse.Loading(true)
                    }
                    is ApiResponse.Success -> {
                        starships.add(response.data)
                    }
                }
            }
            return@combine ApiResponse.Success<List<Starship>>(starships)
        }
    }

}
package com.example.starwarsapp.domain.usecases

import com.example.starwarsapp.data.models.ApiResponse
import com.example.starwarsapp.data.models.Film
import com.example.starwarsapp.data.models.Pagination
import com.example.starwarsapp.data.repositories.FilmsRepository
import com.example.starwarsapp.domain.utils.UrlUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class FilmsUseCase @Inject constructor(
    private val filmsRepository: FilmsRepository
) {

    fun getAllFilms(nextUrl: String?): Flow<ApiResponse<Pagination<Film>>> {
        return filmsRepository.getAllFilms(UrlUtils.getPageNumberFromUrl(nextUrl ?: "0"))
    }

    fun getFilmById(url: String): Flow<ApiResponse<Film>> {
        return filmsRepository.getFilmById(UrlUtils.getIdFromUrl(url))
    }

    fun getAllFilmsByIds(urlList: List<String>): Flow<ApiResponse<List<Film>>> {
        val flows = urlList.map { url ->
            filmsRepository.getFilmById(UrlUtils.getIdFromUrl(url))
        }
        return combine(flows) { responses ->
            val films = mutableListOf<Film>()
            responses.forEach {response ->
                when(response) {
                    is ApiResponse.Error -> {
                        return@combine ApiResponse.Error(response.error)
                    }
                    is ApiResponse.Loading -> {
                        return@combine ApiResponse.Loading(true)
                    }
                    is ApiResponse.Success -> {
                        films.add(response.data)
                    }
                }
            }
            return@combine ApiResponse.Success<List<Film>>(films)
        }
    }

}
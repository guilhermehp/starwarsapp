package com.example.starwarsapp.domain.usecases

import com.example.starwarsapp.data.models.ApiResponse
import com.example.starwarsapp.data.models.Film
import com.example.starwarsapp.data.models.Pagination
import com.example.starwarsapp.data.models.People
import com.example.starwarsapp.data.repositories.PeopleRepository
import com.example.starwarsapp.domain.utils.UrlUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class PeopleUseCase @Inject constructor(private val peopleRepository: PeopleRepository) {

    fun getAllPeople(nextUrl: String?): Flow<ApiResponse<Pagination<People>>> {
        return peopleRepository.getAllPeople(UrlUtils.getPageNumberFromUrl(nextUrl ?: "0"))
    }

    fun getPeopleById(url: String): Flow<ApiResponse<People>> {
        return peopleRepository.getPeopleById(UrlUtils.getIdFromUrl(url))
    }

    fun getAllPeopleByIds(urlList: List<String>): Flow<ApiResponse<List<People>>> {
        val flows = urlList.map { url ->
            peopleRepository.getPeopleById(UrlUtils.getIdFromUrl(url))
        }
        return combine(flows) { responses ->
            val people = mutableListOf<People>()
            responses.forEach {response ->
                when(response) {
                    is ApiResponse.Error -> {
                        return@combine ApiResponse.Error(response.error)
                    }
                    is ApiResponse.Loading -> {
                        return@combine ApiResponse.Loading(true)
                    }
                    is ApiResponse.Success -> {
                        people.add(response.data)
                    }
                }
            }
            return@combine ApiResponse.Success<List<People>>(people)
        }
    }

}
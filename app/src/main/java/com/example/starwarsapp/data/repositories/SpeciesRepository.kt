package com.example.starwarsapp.data.repositories

import com.example.starwarsapp.data.models.ApiResponse
import com.example.starwarsapp.data.models.Pagination
import com.example.starwarsapp.data.models.People
import com.example.starwarsapp.data.models.Species
import com.example.starwarsapp.data.retrofit.ApiService
import com.example.starwarsapp.domain.utils.LocalErrors
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SpeciesRepository @Inject constructor(private val apiService: ApiService) {

    fun getAllSpecies(page: String) : Flow<ApiResponse<Pagination<Species>>> = flow {
        emit(ApiResponse.Loading(isSafe = true))
        val response = apiService.getAllSpecies(page)
        if (response.isSuccessful){
            response.body()?.let { body ->
                emit(ApiResponse.Success(body))
            } ?: run {
                emit(ApiResponse.Error(LocalErrors.EMPTY_LIST))
            }
        } else {
            emit(ApiResponse.Error(response.errorBody()?.string()))
        }
    }

    fun getSpeciesById(id: String): Flow<ApiResponse<Species>> = flow {
        emit(ApiResponse.Loading(isSafe = true))
        val response = apiService.getSpeciesById(id)
        if (response.isSuccessful){
            response.body()?.let { body ->
                emit(ApiResponse.Success(body))
            } ?: run {
                emit(ApiResponse.Error(LocalErrors.EMPTY_LIST))
            }
        } else {
            emit(ApiResponse.Error(response.errorBody()?.string()))
        }
    }
}
package com.example.starwarsapp.data.repositories

import com.example.starwarsapp.data.models.ApiResponse
import com.example.starwarsapp.data.models.Pagination
import com.example.starwarsapp.data.models.Planet
import com.example.starwarsapp.data.retrofit.ApiService
import com.example.starwarsapp.domain.utils.LocalErrors
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PlanetsRepository @Inject constructor (private val apiService: ApiService) {

    fun getAllPlanets(page: String) : Flow<ApiResponse<Pagination<Planet>>> = flow {
        emit(ApiResponse.Loading(isSafe = true))
        val response = apiService.getAllPlanets(page)
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

    fun getPlanetById(id: String): Flow<ApiResponse<Planet>> = flow {
        emit(ApiResponse.Loading(isSafe = true))
        val response = apiService.getPlanetById(id)
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
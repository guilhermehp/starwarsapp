package com.example.starwarsapp.data.repositories

import com.example.starwarsapp.data.models.ApiResponse
import com.example.starwarsapp.data.models.Pagination
import com.example.starwarsapp.data.models.Starship
import com.example.starwarsapp.data.retrofit.ApiService
import com.example.starwarsapp.domain.utils.LocalErrors
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class StarshipsRepository @Inject constructor(private val apiService: ApiService) {

    fun getAllStarships(page: String) : Flow<ApiResponse<Pagination<Starship>>> = flow {
        emit(ApiResponse.Loading(isSafe = true))
        val response = apiService.getAllStarships(page)
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

    fun getStarShipById(id: String): Flow<ApiResponse<Starship>> = flow {
        emit(ApiResponse.Loading(isSafe = true))
        val response = apiService.getStarshipById(id)
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
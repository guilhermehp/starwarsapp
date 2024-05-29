package com.example.starwarsapp.data.repositories

import com.example.starwarsapp.data.models.ApiResponse
import com.example.starwarsapp.data.models.Pagination
import com.example.starwarsapp.data.models.Vehicle
import com.example.starwarsapp.data.retrofit.ApiService
import com.example.starwarsapp.domain.utils.LocalErrors
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class VehiclesRepository @Inject constructor(private val apiService: ApiService) {

    fun getAllVehicles(page: String) : Flow<ApiResponse<Pagination<Vehicle>>> = flow {
        emit(ApiResponse.Loading(isSafe = true))
        val response = apiService.getAllVehicles(page)
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

    fun getVehicleById(id: String): Flow<ApiResponse<Vehicle>> = flow {
        emit(ApiResponse.Loading(isSafe = true))
        val response = apiService.getVehicleById(id)
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
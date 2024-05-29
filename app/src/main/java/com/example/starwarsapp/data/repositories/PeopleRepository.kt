package com.example.starwarsapp.data.repositories

import android.util.Log
import com.example.starwarsapp.data.models.ApiResponse
import com.example.starwarsapp.data.models.Pagination
import com.example.starwarsapp.data.models.People
import com.example.starwarsapp.data.retrofit.ApiService
import com.example.starwarsapp.domain.utils.LocalErrors
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PeopleRepository @Inject constructor(private val apiService: ApiService) {

    fun getAllPeople(page: String) : Flow<ApiResponse<Pagination<People>>> = flow {
        emit(ApiResponse.Loading(isSafe = true))
        val response = apiService.getAllPeople(page)
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

    fun getPeopleById(id: String): Flow<ApiResponse<People>> = flow {
        emit(ApiResponse.Loading(isSafe = true))
        val response = apiService.getPeopleById(id)
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
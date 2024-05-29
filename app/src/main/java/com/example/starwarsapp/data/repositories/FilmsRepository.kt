package com.example.starwarsapp.data.repositories

import com.example.starwarsapp.data.models.ApiResponse
import com.example.starwarsapp.data.models.Film
import com.example.starwarsapp.data.models.Pagination
import com.example.starwarsapp.data.retrofit.ApiService
import com.example.starwarsapp.domain.utils.LocalErrors
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FilmsRepository @Inject constructor (private val apiService: ApiService) {

    fun getAllFilms(page: String) : Flow<ApiResponse<Pagination<Film>>> = flow {
        emit(ApiResponse.Loading(isSafe = true))
        val response = apiService.getAllFilms(page)
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

    fun getFilmById(id: String): Flow<ApiResponse<Film>> = flow {
        emit(ApiResponse.Loading(isSafe = true))
        val response = apiService.getFilmById(id)
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
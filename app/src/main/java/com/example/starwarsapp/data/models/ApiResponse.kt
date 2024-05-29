package com.example.starwarsapp.data.models

sealed class ApiResponse<out T> {
    data class Success<out T>(val data: T): ApiResponse<T>()
    data class Error(val error: String?): ApiResponse<Nothing>()
    data class Loading(val isSafe: Boolean): ApiResponse<Nothing>()
}
package com.example.starwarsapp.data.models

data class Pagination<T>(
    val count: Int?,
    val next: String?,
    val previous: String?,
    val results: List<T> = emptyList()
)
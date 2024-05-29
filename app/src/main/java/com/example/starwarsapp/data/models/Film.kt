package com.example.starwarsapp.data.models

data class Film(
    val characters: List<String> = emptyList(),
    val created: String = "",
    val director: String = "",
    val edited: String = "",
    val episode_id: Int = 0,
    val opening_crawl: String = "",
    val planets: List<String> = emptyList(),
    val producer: String = "",
    val release_date: String = "",
    val species: List<String> = emptyList(),
    val starships: List<String> = emptyList(),
    val title: String = "",
    val url: String = "",
    val vehicles: List<String> = emptyList()
)
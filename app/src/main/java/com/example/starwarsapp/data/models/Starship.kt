package com.example.starwarsapp.data.models

data class Starship(
    val MGLT: String = "",
    val cargo_capacity: String = "",
    val consumables: String = "",
    val cost_in_credits: String = "",
    val created: String = "",
    val crew: String = "",
    val edited: String = "",
    val films: List<String> = emptyList(),
    val hyperdrive_rating: String = "",
    val length: String = "",
    val manufacturer: String = "",
    val max_atmosphering_speed: String = "",
    val model: String = "",
    val name: String = "",
    val passengers: String = "",
    val pilots: List<String> = emptyList(),
    val starship_class: String = "",
    val url: String = ""
)
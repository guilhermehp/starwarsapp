package com.example.starwarsapp.data.models

data class Vehicle(
    val cargo_capacity: String = "",
    val consumables: String = "",
    val cost_in_credits: String = "",
    val created: String = "",
    val crew: String = "",
    val edited: String = "",
    val films: List<String> = emptyList(),
    val length: String = "",
    val manufacturer: String = "",
    val max_atmosphering_speed: String = "",
    val model: String = "",
    val name: String = "",
    val passengers: String = "",
    val pilots: List<String> = emptyList(),
    val url: String = "",
    val vehicle_class: String = ""
)
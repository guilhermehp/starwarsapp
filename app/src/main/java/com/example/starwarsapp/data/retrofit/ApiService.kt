package com.example.starwarsapp.data.retrofit

import com.example.starwarsapp.data.models.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("people/{id}/")
    suspend fun getPeopleById(@Path("id") id: String): Response<People>

    @GET("people/")
    suspend fun getAllPeople(@Query("page") page: String): Response<Pagination<People>>

    @GET("planets/{id}/")
    suspend fun getPlanetById(@Path("id") id: String): Response<Planet>

    @GET("planets/")
    suspend fun getAllPlanets(@Query("page") page: String): Response<Pagination<Planet>>

    @GET("films/{id}/")
    suspend fun getFilmById(@Path("id") id: String): Response<Film>

    @GET("films/")
    suspend fun getAllFilms(@Query("page") page: String): Response<Pagination<Film>>

    @GET("species/{id}/")
    suspend fun getSpeciesById(@Path("id") id: String): Response<Species>

    @GET("species/")
    suspend fun getAllSpecies(@Query("page") page: String): Response<Pagination<Species>>

    @GET("vehicles/{id}/")
    suspend fun getVehicleById(@Path("id") id: String): Response<Vehicle>

    @GET("vehicles/")
    suspend fun getAllVehicles(@Query("page") page: String): Response<Pagination<Vehicle>>

    @GET("starships/{id}/")
    suspend fun getStarshipById(@Path("id") id: String): Response<Starship>

    @GET("starships/")
    suspend fun getAllStarships(@Query("page") page: String): Response<Pagination<Starship>>

}
package com.example.starwarsapp.mocks

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.starwarsapp.data.models.Film
import com.example.starwarsapp.data.models.Pagination
import com.example.starwarsapp.data.models.People
import com.example.starwarsapp.data.models.Planet
import com.example.starwarsapp.data.models.Species
import com.example.starwarsapp.data.models.Starship
import com.example.starwarsapp.data.models.Vehicle
import com.example.starwarsapp.data.retrofit.ApiService
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Response
import java.nio.charset.StandardCharsets
import javax.inject.Inject

class FakeApiService @Inject constructor() : ApiService {
    override suspend fun getPeopleById(id: String): Response<People> {
        return when(id) {
            "1" -> {
                val context: Context = ApplicationProvider.getApplicationContext()
                val inputStream = context.assets.open("lukeSkywalker.json")
                val jsonString = inputStream.readBytes().toString(StandardCharsets.UTF_8)
                val response: People = Gson().fromJson(jsonString, People::class.java)
                Response.success(response)
            }
            "2" -> {
                val context: Context = ApplicationProvider.getApplicationContext()
                val inputStream = context.assets.open("c3po.json")
                val jsonString = inputStream.readBytes().toString(StandardCharsets.UTF_8)
                val response: People = Gson().fromJson(jsonString, People::class.java)
                Response.success(response)
            }
            "66" -> {
                val context: Context = ApplicationProvider.getApplicationContext()
                val inputStream = context.assets.open("dorme.json")
                val jsonString = inputStream.readBytes().toString(StandardCharsets.UTF_8)
                val response: People = Gson().fromJson(jsonString, People::class.java)
                Response.success(response)
            }
            "67" -> {
                val context: Context = ApplicationProvider.getApplicationContext()
                val inputStream = context.assets.open("dooku.json")
                val jsonString = inputStream.readBytes().toString(StandardCharsets.UTF_8)
                val response: People = Gson().fromJson(jsonString, People::class.java)
                Response.success(response)
            }
            else -> {
                Response.success(People())
            }
        }
    }

    override suspend fun getAllPeople(page: String): Response<Pagination<People>> {
        return if(page.isEmpty() || page == "0"){
            val context: Context = ApplicationProvider.getApplicationContext()
            val inputStream = context.assets.open("peopleListPageOne.json")
            val jsonString = inputStream.readBytes().toString(StandardCharsets.UTF_8)
            val paginationType = object : TypeToken<Pagination<People>>() {}.type
            val response: Pagination<People> = Gson().fromJson(jsonString, paginationType)
            Response.success(response)
        } else {
            val context: Context = ApplicationProvider.getApplicationContext()
            val inputStream = context.assets.open("peopleListPageTwo.json")
            val jsonString = inputStream.readBytes().toString(StandardCharsets.UTF_8)
            val paginationType = object : TypeToken<Pagination<People>>() {}.type
            val response: Pagination<People> = Gson().fromJson(jsonString, paginationType)
            Response.success(response)
        }
    }

    override suspend fun getPlanetById(id: String): Response<Planet> {
        return when(id) {
            "1" -> {
                val context: Context = ApplicationProvider.getApplicationContext()
                val inputStream = context.assets.open("tatooine.json")
                val jsonString = inputStream.readBytes().toString(StandardCharsets.UTF_8)
                val response: Planet = Gson().fromJson(jsonString, Planet::class.java)
                Response.success(response)
            }
            "2" -> {
                val context: Context = ApplicationProvider.getApplicationContext()
                val inputStream = context.assets.open("alderaan.json")
                val jsonString = inputStream.readBytes().toString(StandardCharsets.UTF_8)
                val response: Planet = Gson().fromJson(jsonString, Planet::class.java)
                Response.success(response)
            }
            "9" -> {
                val context: Context = ApplicationProvider.getApplicationContext()
                val inputStream = context.assets.open("coruscant.json")
                val jsonString = inputStream.readBytes().toString(StandardCharsets.UTF_8)
                val response: Planet = Gson().fromJson(jsonString, Planet::class.java)
                Response.success(response)
            }
            else -> {
                Response.success(Planet())
            }
        }
    }

    override suspend fun getAllPlanets(page: String): Response<Pagination<Planet>> {
        return if(page.isEmpty() || page == "0"){
            val context: Context = ApplicationProvider.getApplicationContext()
            val inputStream = context.assets.open("planetListPageOne.json")
            val jsonString = inputStream.readBytes().toString(StandardCharsets.UTF_8)
            val paginationType = object : TypeToken<Pagination<Planet>>() {}.type
            val response: Pagination<Planet> = Gson().fromJson(jsonString, paginationType)
            Response.success(response)
        } else {
            val context: Context = ApplicationProvider.getApplicationContext()
            val inputStream = context.assets.open("planetListPageTwo.json")
            val jsonString = inputStream.readBytes().toString(StandardCharsets.UTF_8)
            val paginationType = object : TypeToken<Pagination<Planet>>() {}.type
            val response: Pagination<Planet> = Gson().fromJson(jsonString, paginationType)
            Response.success(response)
        }
    }

    override suspend fun getFilmById(id: String): Response<Film> {
        return when(id) {
            "1" -> {
                val context: Context = ApplicationProvider.getApplicationContext()
                val inputStream = context.assets.open("aNewHope.json")
                val jsonString = inputStream.readBytes().toString(StandardCharsets.UTF_8)
                val response: Film = Gson().fromJson(jsonString, Film::class.java)
                Response.success(response)
            }
            "2" -> {
                val context: Context = ApplicationProvider.getApplicationContext()
                val inputStream = context.assets.open("theEmpireStrikesBack.json")
                val jsonString = inputStream.readBytes().toString(StandardCharsets.UTF_8)
                val response: Film = Gson().fromJson(jsonString, Film::class.java)
                Response.success(response)
            }
            "3" -> {
                val context: Context = ApplicationProvider.getApplicationContext()
                val inputStream = context.assets.open("returnOfTheJedi.json")
                val jsonString = inputStream.readBytes().toString(StandardCharsets.UTF_8)
                val response: Film = Gson().fromJson(jsonString, Film::class.java)
                Response.success(response)
            }
            "6" -> {
                val context: Context = ApplicationProvider.getApplicationContext()
                val inputStream = context.assets.open("revengeOfTheSith.json")
                val jsonString = inputStream.readBytes().toString(StandardCharsets.UTF_8)
                val response: Film = Gson().fromJson(jsonString, Film::class.java)
                Response.success(response)
            }
            else -> {
                Response.success(Film())
            }
        }
    }

    override suspend fun getAllFilms(page: String): Response<Pagination<Film>> {
        val context: Context = ApplicationProvider.getApplicationContext()
        val inputStream = context.assets.open("filmList.json")
        val jsonString = inputStream.readBytes().toString(StandardCharsets.UTF_8)
        val paginationType = object : TypeToken<Pagination<Film>>() {}.type
        val response: Pagination<Film> = Gson().fromJson(jsonString, paginationType)
        return Response.success(response)
    }

    override suspend fun getSpeciesById(id: String): Response<Species> {
        return when(id) {
            "1" -> {
                val context: Context = ApplicationProvider.getApplicationContext()
                val inputStream = context.assets.open("human.json")
                val jsonString = inputStream.readBytes().toString(StandardCharsets.UTF_8)
                val response: Species = Gson().fromJson(jsonString, Species::class.java)
                Response.success(response)
            }
            "2" -> {
                val context: Context = ApplicationProvider.getApplicationContext()
                val inputStream = context.assets.open("droid.json")
                val jsonString = inputStream.readBytes().toString(StandardCharsets.UTF_8)
                val response: Species = Gson().fromJson(jsonString, Species::class.java)
                Response.success(response)
            }
            else -> {
                Response.success(Species())
            }
        }
    }

    override suspend fun getAllSpecies(page: String): Response<Pagination<Species>> {
        return if(page.isEmpty() || page == "0"){
            val context: Context = ApplicationProvider.getApplicationContext()
            val inputStream = context.assets.open("speciesListPageOne.json")
            val jsonString = inputStream.readBytes().toString(StandardCharsets.UTF_8)
            val paginationType = object : TypeToken<Pagination<Species>>() {}.type
            val response: Pagination<Species> = Gson().fromJson(jsonString, paginationType)
            Response.success(response)
        } else {
            val context: Context = ApplicationProvider.getApplicationContext()
            val inputStream = context.assets.open("speciesListPageTwo.json")
            val jsonString = inputStream.readBytes().toString(StandardCharsets.UTF_8)
            val paginationType = object : TypeToken<Pagination<Species>>() {}.type
            val response: Pagination<Species> = Gson().fromJson(jsonString, paginationType)
            Response.success(response)
        }
    }

    override suspend fun getVehicleById(id: String): Response<Vehicle> {
        return when(id) {
            "14" -> {
                val context: Context = ApplicationProvider.getApplicationContext()
                val inputStream = context.assets.open("snowspeeder.json")
                val jsonString = inputStream.readBytes().toString(StandardCharsets.UTF_8)
                val response: Vehicle = Gson().fromJson(jsonString, Vehicle::class.java)
                Response.success(response)
            }
            "30" -> {
                val context: Context = ApplicationProvider.getApplicationContext()
                val inputStream = context.assets.open("imperialSpeederBike.json")
                val jsonString = inputStream.readBytes().toString(StandardCharsets.UTF_8)
                val response: Vehicle = Gson().fromJson(jsonString, Vehicle::class.java)
                Response.success(response)
            }
            "4" -> {
                val context: Context = ApplicationProvider.getApplicationContext()
                val inputStream = context.assets.open("sandCrawler.json")
                val jsonString = inputStream.readBytes().toString(StandardCharsets.UTF_8)
                val response: Vehicle = Gson().fromJson(jsonString, Vehicle::class.java)
                Response.success(response)
            }
            "6" -> {
                val context: Context = ApplicationProvider.getApplicationContext()
                val inputStream = context.assets.open("t16Skyhopper.json")
                val jsonString = inputStream.readBytes().toString(StandardCharsets.UTF_8)
                val response: Vehicle = Gson().fromJson(jsonString, Vehicle::class.java)
                Response.success(response)
            }
            else -> {
                Response.success(Vehicle())
            }
        }
    }

    override suspend fun getAllVehicles(page: String): Response<Pagination<Vehicle>> {
        return if(page.isEmpty() || page == "0"){
            val context: Context = ApplicationProvider.getApplicationContext()
            val inputStream = context.assets.open("vehicleListPageOne.json")
            val jsonString = inputStream.readBytes().toString(StandardCharsets.UTF_8)
            val paginationType = object : TypeToken<Pagination<Vehicle>>() {}.type
            val response: Pagination<Vehicle> = Gson().fromJson(jsonString, paginationType)
            Response.success(response)
        } else {
            val context: Context = ApplicationProvider.getApplicationContext()
            val inputStream = context.assets.open("vehicleListPageTwo.json")
            val jsonString = inputStream.readBytes().toString(StandardCharsets.UTF_8)
            val paginationType = object : TypeToken<Pagination<Vehicle>>() {}.type
            val response: Pagination<Vehicle> = Gson().fromJson(jsonString, paginationType)
            Response.success(response)
        }
    }

    override suspend fun getStarshipById(id: String): Response<Starship> {
        return when(id) {
            "12" -> {
                val context: Context = ApplicationProvider.getApplicationContext()
                val inputStream = context.assets.open("xWing.json")
                val jsonString = inputStream.readBytes().toString(StandardCharsets.UTF_8)
                val response: Starship = Gson().fromJson(jsonString, Starship::class.java)
                Response.success(response)
            }
            "22" -> {
                val context: Context = ApplicationProvider.getApplicationContext()
                val inputStream = context.assets.open("imperialShuttle.json")
                val jsonString = inputStream.readBytes().toString(StandardCharsets.UTF_8)
                val response: Starship = Gson().fromJson(jsonString, Starship::class.java)
                Response.success(response)
            }
            "2" -> {
                val context: Context = ApplicationProvider.getApplicationContext()
                val inputStream = context.assets.open("cr90Corvette.json")
                val jsonString = inputStream.readBytes().toString(StandardCharsets.UTF_8)
                val response: Starship = Gson().fromJson(jsonString, Starship::class.java)
                Response.success(response)
            }
            "3" -> {
                val context: Context = ApplicationProvider.getApplicationContext()
                val inputStream = context.assets.open("starDestroyer.json")
                val jsonString = inputStream.readBytes().toString(StandardCharsets.UTF_8)
                val response: Starship = Gson().fromJson(jsonString, Starship::class.java)
                Response.success(response)
            }
            else -> {
                Response.success(Starship())
            }
        }
    }

    override suspend fun getAllStarships(page: String): Response<Pagination<Starship>> {
        return if(page.isEmpty() || page == "0"){
            val context: Context = ApplicationProvider.getApplicationContext()
            val inputStream = context.assets.open("starshipListPageOne.json")
            val jsonString = inputStream.readBytes().toString(StandardCharsets.UTF_8)
            val paginationType = object : TypeToken<Pagination<Starship>>() {}.type
            val response: Pagination<Starship> = Gson().fromJson(jsonString, paginationType)
            Response.success(response)
        } else {
            val context: Context = ApplicationProvider.getApplicationContext()
            val inputStream = context.assets.open("starshipListPageTwo.json")
            val jsonString = inputStream.readBytes().toString(StandardCharsets.UTF_8)
            val paginationType = object : TypeToken<Pagination<Starship>>() {}.type
            val response: Pagination<Starship> = Gson().fromJson(jsonString, paginationType)
            Response.success(response)
        }
    }

}
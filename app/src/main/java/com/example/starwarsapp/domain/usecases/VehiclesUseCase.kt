package com.example.starwarsapp.domain.usecases

import com.example.starwarsapp.data.models.ApiResponse
import com.example.starwarsapp.data.models.Pagination
import com.example.starwarsapp.data.models.Starship
import com.example.starwarsapp.data.models.Vehicle
import com.example.starwarsapp.data.repositories.VehiclesRepository
import com.example.starwarsapp.domain.utils.UrlUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class VehiclesUseCase @Inject constructor(private val vehiclesRepository: VehiclesRepository) {

    fun getAllVehicles(nextUrl: String?): Flow<ApiResponse<Pagination<Vehicle>>> {
        return vehiclesRepository.getAllVehicles(UrlUtils.getPageNumberFromUrl(nextUrl ?: "0"))
    }

    fun getVehicleById(url: String): Flow<ApiResponse<Vehicle>> {
        return vehiclesRepository.getVehicleById(UrlUtils.getIdFromUrl(url))
    }

    fun getAllVehiclesByIds(urlList: List<String>): Flow<ApiResponse<List<Vehicle>>> {
        val flows = urlList.map { url ->
            vehiclesRepository.getVehicleById(UrlUtils.getIdFromUrl(url))
        }
        return combine(flows) { responses ->
            val vehicles = mutableListOf<Vehicle>()
            responses.forEach {response ->
                when(response) {
                    is ApiResponse.Error -> {
                        return@combine ApiResponse.Error(response.error)
                    }
                    is ApiResponse.Loading -> {
                        return@combine ApiResponse.Loading(true)
                    }
                    is ApiResponse.Success -> {
                        vehicles.add(response.data)
                    }
                }
            }
            return@combine ApiResponse.Success<List<Vehicle>>(vehicles)
        }
    }

}
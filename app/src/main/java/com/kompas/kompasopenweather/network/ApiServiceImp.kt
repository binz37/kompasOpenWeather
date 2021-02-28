package com.kompas.kompasopenweather.network

import com.kompas.kompasopenweather.model.City
import javax.inject.Inject

class ApiServiceImp @Inject constructor(private val apiService: ApiService) {
    suspend fun getCity(city: String, appId: String, mode: String, units: String): City = apiService.getCityData(city, appId, mode, units)
}
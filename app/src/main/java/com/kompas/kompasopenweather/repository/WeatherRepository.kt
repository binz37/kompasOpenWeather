package com.kompas.kompasopenweather.repository

import com.kompas.kompasopenweather.common.Common.Companion.OPEN_WEATHER_KEY
import com.kompas.kompasopenweather.model.City
import com.kompas.kompasopenweather.network.ApiServiceImp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val apiServiceImp: ApiServiceImp) {
    fun getCityData(city: String): Flow<City> = flow {
        val response = apiServiceImp.getCity(city, OPEN_WEATHER_KEY, "json", "metric")
        emit(response)
    }.flowOn(Dispatchers.IO).conflate()
}
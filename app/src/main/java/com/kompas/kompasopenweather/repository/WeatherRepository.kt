package com.kompas.kompasopenweather.repository

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
        val response = apiServiceImp.getCity(city, "994ca6f75926ffa923f3a70dc1612dcf", "json", "metric")
        emit(response)
    }.flowOn(Dispatchers.IO).conflate()
}
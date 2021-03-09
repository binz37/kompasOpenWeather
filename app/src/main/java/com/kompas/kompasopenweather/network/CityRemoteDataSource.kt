package com.kompas.kompasopenweather.network

import javax.inject.Inject

class CityRemoteDataSource @Inject constructor(
    private val cityService: CityService
): BaseDataSource() {
    suspend fun getCity(q: String, appId: String, mode: String, units: String) = getResult {
        cityService.getCity(q, appId, mode, units)
    }
}
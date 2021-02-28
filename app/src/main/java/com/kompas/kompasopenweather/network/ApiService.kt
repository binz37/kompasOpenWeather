package com.kompas.kompasopenweather.network

import com.kompas.kompasopenweather.model.City
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("weather/")
    suspend fun getCityData(
        @Query("q") q: String,
        @Query("appid") appId: String,
        @Query("mode") mode: String,
        @Query("units") units: String
    ): City
}
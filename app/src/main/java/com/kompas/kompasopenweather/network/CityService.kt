package com.kompas.kompasopenweather.network

import com.kompas.kompasopenweather.model.City
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CityService {
    @GET("weather/")
    suspend fun getCity(
        @Query("q") q: String,
        @Query("appid") appId: String,
        @Query("mode") mode: String,
        @Query("units") units: String,
    ): Response<City>
}
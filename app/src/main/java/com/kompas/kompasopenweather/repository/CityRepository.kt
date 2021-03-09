package com.kompas.kompasopenweather.repository

import com.kompas.kompasopenweather.common.Common
import com.kompas.kompasopenweather.local.ICityDao
import com.kompas.kompasopenweather.network.CityRemoteDataSource
import com.kompas.kompasopenweather.utils.DataAccessStrategy
import javax.inject.Inject

class CityRepository @Inject constructor(
    private val remoteDataSource: CityRemoteDataSource,
    private val localDataSource: ICityDao,
) {
    fun getCity(q: String) =
        DataAccessStrategy.performGetOperation(
            databaseQuery = { localDataSource.getCityFromLocal() },
            networkCall = { remoteDataSource.getCity(q, Common.OPEN_WEATHER_KEY, Common.MODE, Common.UNIT) },
            saveCallResult = { localDataSource.saveToLocal(it) }
        )
}
package com.kompas.kompasopenweather.module

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.kompas.kompasopenweather.common.Common
import com.kompas.kompasopenweather.local.AppDatabase
import com.kompas.kompasopenweather.local.ICityDao
import com.kompas.kompasopenweather.network.CityRemoteDataSource
import com.kompas.kompasopenweather.network.CityService
import com.kompas.kompasopenweather.repository.CityRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class AppModule {
    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson): Retrofit = Retrofit.Builder()
        .baseUrl(Common.baseURL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun provideCityService(retrofit: Retrofit): CityService = retrofit.create(CityService::class.java)

    @Singleton
    @Provides
    fun provideRemoteDataSource(cityService: CityService) = CityRemoteDataSource(cityService)

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) = AppDatabase.getDatabase(appContext)

    @Singleton
    @Provides
    fun provideCityDao(db: AppDatabase) = db.cityDao()

    fun provideRepository(remoteDataSource: CityRemoteDataSource
                          , localDataSource: ICityDao
    ) = CityRepository(remoteDataSource, localDataSource)
}
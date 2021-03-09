package com.kompas.kompasopenweather.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kompas.kompasopenweather.model.City

@Dao
interface ICityDao {
    @Query("SELECT * FROM City")
    fun getCityFromLocal() : LiveData<City>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveToLocal(city: City)
}
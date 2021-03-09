package com.kompas.kompasopenweather.model.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.kompas.kompasopenweather.model.Main
import com.kompas.kompasopenweather.model.Sys
import com.kompas.kompasopenweather.model.Weather
import com.kompas.kompasopenweather.model.Wind

class Converters {
    @TypeConverter
    fun windToJson(value: Wind) = Gson().toJson(value)

    @TypeConverter
    fun jsonToWind(value: String) = Gson().fromJson(value, Wind::class.java)

    @TypeConverter
    fun mainToJson(value: Main) = Gson().toJson(value)

    @TypeConverter
    fun jsonToMain(value: String) = Gson().fromJson(value, Main::class.java)

    @TypeConverter
    fun sysToJson(value: Sys) = Gson().toJson(value)

    @TypeConverter
    fun jsonToSys(value: String) = Gson().fromJson(value, Sys::class.java)

    @TypeConverter
    fun listToJson(value: List<Weather>) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<Weather>::class.java).toList()
}
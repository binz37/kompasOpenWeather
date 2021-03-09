package com.kompas.kompasopenweather.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "City")
data class City(
    val weather: List<Weather>,
    val main: Main,
    val wind: Wind,
    val sys: Sys,
    val name: String,
    @PrimaryKey
    val id: String
)
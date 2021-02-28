package com.kompas.kompasopenweather.model

data class City(
    val weather: ArrayList<Weather>,
    val main: Main,
    val wind: Wind,
    val sys: Sys,
    val name: String
)
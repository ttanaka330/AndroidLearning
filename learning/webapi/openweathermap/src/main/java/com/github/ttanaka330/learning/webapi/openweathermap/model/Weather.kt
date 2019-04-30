package com.github.ttanaka330.learning.webapi.openweathermap.model

data class Weather(
    val datetime: String,
    val weather: String,
    val icon: String,
    val kelvin: Double
)
package com.github.ttanaka330.learning.webapi.openweathermap.data

import com.google.gson.annotations.SerializedName

/**
 * @see <a href="https://openweathermap.org/forecast5">OpenWeatherMap - 5 day weather forecast</a>
 */
class WeatherForecastResponse(
    val cod: String,
    val message: Double,
    val cnt: Int,
    val list: List<Data>
) {
    inner class Data(
        val dt: Int,
        val main: Main,
        val weather: List<Weather>,
        val clouds: Clouds,
        val wind: Wind,
        val rain: Rain,
        val sys: Sys,
        val dtTxt: String
    ) {
        inner class Main(
            val temp: Double,
            val tempMin: Double,
            val tempMax: Double,
            val pressure: Double,
            val seaLevel: Double,
            val grndLevel: Double,
            val humidity: Int,
            val tempKf: Double
        )

        inner class Weather(
            val id: Int,
            val main: String,
            val description: String,
            val icon: String
        )

        inner class Clouds(
            val all: Int
        )

        inner class Wind(
            val speed: Double,
            val deg: Double
        )

        inner class Rain(
            @SerializedName("3h") val d3h: Double
        )

        inner class Sys(
            val pod: String
        )
    }
}
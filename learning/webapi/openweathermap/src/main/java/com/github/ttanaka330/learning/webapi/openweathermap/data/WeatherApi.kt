package com.github.ttanaka330.learning.webapi.openweathermap.data

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @see <a href="https://openweathermap.org/">OpenWeatherMap</a>
 */
interface WeatherApi {

    companion object {
        const val ENDPOINT = "http://api.openweathermap.org"
        const val ICON_URL = "http://openweathermap.org/img/w/"
        const val API_KEY = "6505d0e6056d1807d2a13859bf1caf2e"
    }

    @GET("/data/2.5/forecast")
    fun getWeather(
        @Query("appid") appId: String,
        @Query("id") cityId: String
    ): Observable<WeatherForecastResponse>
}
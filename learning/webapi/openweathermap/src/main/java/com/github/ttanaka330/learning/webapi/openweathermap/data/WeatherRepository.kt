package com.github.ttanaka330.learning.webapi.openweathermap.data

import com.github.ttanaka330.learning.webapi.openweathermap.model.City
import com.github.ttanaka330.learning.webapi.openweathermap.model.Weather
import io.reactivex.Observable
import io.reactivex.Single

interface WeatherRepository {

    fun getCity(): Single<List<City>>

    fun getWeather(cityId: String): Observable<List<Weather>>
}
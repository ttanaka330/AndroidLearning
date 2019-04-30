package com.github.ttanaka330.learning.webapi.openweathermap.data

import android.annotation.SuppressLint
import android.content.Context
import com.github.ttanaka330.learning.webapi.openweathermap.BuildConfig
import com.github.ttanaka330.learning.webapi.openweathermap.model.City
import com.github.ttanaka330.learning.webapi.openweathermap.model.Weather
import com.github.ttanaka330.learning.webapi.openweathermap.utils.FileUtils
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.reactivex.Observable
import io.reactivex.Single
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.*

class WeatherRepositoryImpl(
    private val context: Context,
    private val retrofit: Retrofit
) : WeatherRepository {

    companion object {
        private const val FILE_CITY = "city_japan.txt"
        private const val FILE_SPLIT = "\t"
        private const val IMAGE_EXTENSION = ".png"

        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var INSTANCE: WeatherRepositoryImpl? = null

        fun getInstance(context: Context): WeatherRepository =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: run {
                    val retrofit = createRetrofit()
                    WeatherRepositoryImpl(context, retrofit).also { INSTANCE = it }
                }
            }

        private fun createRetrofit(): Retrofit = Retrofit.Builder()
            .client(createClient())
            .baseUrl(WeatherApi.ENDPOINT)
            .addConverterFactory(GsonConverterFactory.create(createGson()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        private fun createClient(): OkHttpClient = OkHttpClient.Builder().apply {
            addInterceptor(loggerInterceptor)
        }.build()

        private fun createGson(): Gson = GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create()

        private val loggerInterceptor: Interceptor = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.BASIC
            }
        }
    }

    override fun getCity(): Single<List<City>> = Single.create<List<City>> {
        val list = ArrayList<City>()
        try {
            val cityData = FileUtils.readAssetsTextFile(context, FILE_CITY)
            for (data in cityData) {
                val values = data.split(FILE_SPLIT.toRegex())
                    .dropLastWhile { it.isEmpty() }
                    .toTypedArray()
                list.add(City(values[0], values[1]))
            }
            it.onSuccess(list)
        } catch (e: IOException) {
            it.onError(e)
        }
    }

    override fun getWeather(cityId: String): Observable<List<Weather>> {
        return retrofit.create(WeatherApi::class.java)
            .getWeather(WeatherApi.API_KEY, cityId)
            .map {
                it.list.map { data -> data.toWeather() }
            }
    }

    private fun WeatherForecastResponse.Data.toWeather(): Weather {
        val weather = weather[0]
        return Weather(
            datetime = dtTxt,
            weather = weather.main,
            icon = WeatherApi.ICON_URL + weather.icon + IMAGE_EXTENSION,
            kelvin = main.temp
        )
    }
}
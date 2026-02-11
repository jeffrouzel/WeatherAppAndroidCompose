package com.example.weatherappandroidcompose.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("/data/2.5/weather?")
    suspend fun getWeather(
        @Query("key") apiKey: String,
        @Query("q") city: String,
    ) : Response<WeatherModel>
}
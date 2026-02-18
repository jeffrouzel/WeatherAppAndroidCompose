package com.example.weatherappandroidcompose.repository

import com.example.weatherappandroidcompose.api.Constants
import com.example.weatherappandroidcompose.api.OpenWeatherResponse
import com.example.weatherappandroidcompose.api.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

interface WeatherRepository {
    fun getWeatherByCity(
        city: String,
        onSuccess: (OpenWeatherResponse) -> Unit,
        onError: (String) -> Unit
    )
}

class WeatherRepositoryImpl : WeatherRepository {
    private val api = RetrofitClient.api

    override fun getWeatherByCity(
        city: String,
        onSuccess: (OpenWeatherResponse) -> Unit,
        onError: (String) -> Unit
    ) {
        api.getWeather(city, Constants.apiKey)
            .enqueue(object : Callback<OpenWeatherResponse> {
                override fun onResponse(
                    call: Call<OpenWeatherResponse>,
                    response: Response<OpenWeatherResponse>
                ) {
                    if (response.isSuccessful) {
                        val weather = response.body()
                        if (weather != null) {
                            onSuccess(weather)
                        } else {
                            onError("No weather data found")
                        }
                    } else {
                        when (response.code()) {
                            404 -> onError("City not found. Please check spelling.")
                            401 -> onError("Invalid API key")
                            429 -> onError("Too many requests. Try again later.")
                            else -> onError("Error: ${response.code()} - ${response.message()}")
                        }
                    }
                }
                override fun onFailure(call: Call<OpenWeatherResponse>, t: Throwable) {
                    val message = when (t) {
                        is java.net.UnknownHostException -> "No internet connection. Please check your network."
                        is java.net.SocketTimeoutException -> "Connection timed out. Please try again."
                        is java.io.IOException -> "Network error. Please check your connection."
                        else -> "Something went wrong: ${t.message ?: "Unknown error"}"
                    }
                    onError(message)
                }
            })
    }
}
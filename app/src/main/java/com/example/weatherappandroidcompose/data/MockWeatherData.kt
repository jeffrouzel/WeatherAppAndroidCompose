package com.example.weatherappandroidcompose.data

data class WeatherCity(
    val id: Int,
    val cityName: String,
    val country: String,
    val temperature: Int,
    val condition: String,
    val humidity: Int,
    val windSpeed: Int
)

// Mock data for testing
object MockWeatherData {
    val cities = listOf(
        WeatherCity(
            id = 1,
            cityName = "Manila",
            country = "Philippines",
            temperature = 32,
            condition = "☀\uFE0F Sunny",
            humidity = 75,
            windSpeed = 15
        ),
        WeatherCity(
            id = 2,
            cityName = "Cebu",
            country = "Philippines",
            temperature = 30,
            condition = "☁\uFE0F Partly Cloudy",
            humidity = 70,
            windSpeed = 12
        ),
        WeatherCity(
            id = 3,
            cityName = "Davao",
            country = "Philippines",
            temperature = 31,
            condition = "\uD83C\uDF27\uFE0F Rainy",
            humidity = 85,
            windSpeed = 20
        ),
        WeatherCity(
            id = 4,
            cityName = "Tokyo",
            country = "Japan",
            temperature = 22,
            condition = "Cloudy",
            humidity = 60,
            windSpeed = 10
        ),
        WeatherCity(
            id = 5,
            cityName = "Sieoul",
            country = "South Korea",
            temperature = 18,
            condition = "Clear",
            humidity = 55,
            windSpeed = 8
        ),
        WeatherCity(
            id = 6,
            cityName = "Singapore",
            country = "Singapore",
            temperature = 33,
            condition = "Thunderstorm",
            humidity = 90,
            windSpeed = 25
        ),
        WeatherCity(
            id = 7,
            cityName = "Bangkok",
            country = "Thailand",
            temperature = 34,
            condition = "Hot",
            humidity = 80,
            windSpeed = 5
        ),
        WeatherCity(
            id = 8,
            cityName = "Hong Kong",
            country = "China",
            temperature = 28,
            condition = "Foggy",
            humidity = 78,
            windSpeed = 14
        ),
        WeatherCity(
            id = 9,
            cityName = "Baguio",
            country = "Philippines",
            temperature = 20,
            condition = "Cool",
            humidity = 88,
            windSpeed = 18
        ),
        WeatherCity(
            id = 10,
            cityName = "Quezon City",
            country = "Philippines",
            temperature = 31,
            condition = "Sunny",
            humidity = 72,
            windSpeed = 16
        )
    )
}
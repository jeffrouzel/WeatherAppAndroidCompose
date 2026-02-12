package com.example.weatherappandroidcompose.data

data class WeatherCity(
    val id: Int,
    val cityName: String,
    val country: String,
    val temperature: Int,
    val condition: String,
    val humidity: Int,
    val sunrise: String,
    val sunset: String
)

// Mock data for testing
object MockWeatherData {
    val cities = listOf(
        WeatherCity(
            id = 1,
            cityName = "Manila",
            country = "Philippines",
            temperature = 32,
            condition = "Sunny",
            humidity = 75,
            sunrise = "5:00",
            sunset = "6:00"
        ),
        WeatherCity(
            id = 2,
            cityName = "Cebu",
            country = "Philippines",
            temperature = 30,
            condition = "Partly Cloudy",
            humidity = 70,
            sunrise = "5:00",
            sunset = "6:00"
        ),
        WeatherCity(
            id = 3,
            cityName = "Davao",
            country = "Philippines",
            temperature = 31,
            condition = "Rainy",
            humidity = 85,
            sunrise = "5:00",
            sunset = "6:00"
        ),
        WeatherCity(
            id = 4,
            cityName = "Tokyo",
            country = "Japan",
            temperature = 22,
            condition = "Cloudy",
            humidity = 60,
            sunrise = "5:00",
            sunset = "6:00"
        ),
        WeatherCity(
            id = 5,
            cityName = "Sieoul",
            country = "South Korea",
            temperature = 18,
            condition = "Clear",
            humidity = 55,
            sunrise = "5:00",
            sunset = "6:00"
        ),
        WeatherCity(
            id = 6,
            cityName = "Singapore",
            country = "Singapore",
            temperature = 33,
            condition = "Thunderstorm",
            humidity = 90,
            sunrise = "5:00",
            sunset = "6:00"
        ),
        WeatherCity(
            id = 7,
            cityName = "Bangkok",
            country = "Thailand",
            temperature = 34,
            condition = "Hot",
            humidity = 80,
            sunrise = "5:00",
            sunset = "6:00"
        ),
        WeatherCity(
            id = 8,
            cityName = "Hong Kong",
            country = "China",
            temperature = 28,
            condition = "Foggy",
            humidity = 78,
            sunrise = "5:00",
            sunset = "6:00"
        ),
        WeatherCity(
            id = 9,
            cityName = "Baguio",
            country = "Philippines",
            temperature = 20,
            condition = "Cool",
            humidity = 88,
            sunrise = "5:00",
            sunset = "6:00"
        ),
        WeatherCity(
            id = 10,
            cityName = "Quezon City",
            country = "Philippines",
            temperature = 31,
            condition = "Sunny",
            humidity = 72,
            sunrise = "5:00",
            sunset = "6:00"
        )
    )
}
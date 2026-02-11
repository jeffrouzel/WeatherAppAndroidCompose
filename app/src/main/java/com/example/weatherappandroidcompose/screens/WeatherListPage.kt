package com.example.weatherappandroidcompose.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weatherappandroidcompose.api.NetworkResponse
import com.example.weatherappandroidcompose.api.WeatherModel
import com.example.weatherappandroidcompose.data.MockWeatherData
import com.example.weatherappandroidcompose.data.WeatherCity
import com.example.weatherappandroidcompose.ui.theme.WeatherAppAndroidComposeTheme
import com.example.weatherappandroidcompose.ui.theme.mainscreenBackgroundModifier

@Composable
fun WeatherListScreen(viewModel: WeatherViewModel) {
    var searchQuery by rememberSaveable { mutableStateOf("") }
    val weatherData by viewModel.weatherData.observeAsState()

    val weatherList = rememberSaveable { mutableStateListOf<WeatherModel>() }

    LaunchedEffect(weatherData) {
        if (weatherData is NetworkResponse.Success) {
            val weather = (weatherData as NetworkResponse.Success).data
            // Check if city already exists in list, if not add it
            if (weatherList.none { it.name == weather.name && it.sys.country == weather.sys.country }) {
                weatherList.add(0, weather) // Add to beginning of list
            }
        }
    }


    // Filter cities based on search query
    val filteredCities = rememberSaveable(searchQuery) {
        if (searchQuery.isEmpty()) {
            MockWeatherData.cities
        } else {
            MockWeatherData.cities.filter { city ->
                city.cityName.contains(searchQuery, ignoreCase = true) ||
                        city.country.contains(searchQuery, ignoreCase = true)
            }
        }
    }

    Column(
        modifier = mainscreenBackgroundModifier()
    ) {
        SearchBar(
            query = searchQuery,
            onQueryChange = { searchQuery = it }
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (filteredCities.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No cities found",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                )
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(filteredCities) { city ->
                    WeatherCityCard(city = city)
                }
            }
        }
    }
}

@Composable
fun WeatherCityCard(city: WeatherCity) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        // Card Contents
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Content 1
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Content 1 (PLACE)
                Column {
                    Text(
                        text = city.cityName,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = city.country,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                    )
                }

                // Content 1 (TEMPERATURE)
                Text(
                    text = "${city.temperature}°C",
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Content 2 (CONDITION)
            Text(
                text = city.condition,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Content 3 (ADDITIONAL INFO)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                WeatherInfoItem(
                    label = "Humidity",
                    value = "${city.humidity}%"
                )
                WeatherInfoItem(
                    label = "Wind",
                    value = "${city.windSpeed} km/h"
                )
            }
        }
    }
}

@Composable
fun WeatherInfoItem(label: String, value: String) {
    Column(
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = label,
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
        )
        Text(
            text = value,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

////////////////////////////////////////////// Previews //////////////////////////////////////////////

@Preview(showBackground = true)
@Composable
private fun WeatherCityCardPreview() {
    WeatherAppAndroidComposeTheme {
        WeatherCityCard(
            city = WeatherCity(
                id = 1,
                cityName = "Manila",
                country = "Philippines",
                temperature = 32,
                condition = "Sunny",
                humidity = 75,
                windSpeed = 15
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun WeatherListScreenPreview() {
    WeatherAppAndroidComposeTheme {
        WeatherListScreen(viewModel = viewModel())
    }
}

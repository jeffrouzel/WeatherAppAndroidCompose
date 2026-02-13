package com.example.weatherappandroidcompose.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherappandroidcompose.api.OpenWeatherResponse
import com.example.weatherappandroidcompose.ui.theme.WeatherAppAndroidComposeTheme
import com.example.weatherappandroidcompose.ui.theme.mainscreenBackgroundModifier
import com.example.weatherappandroidcompose.viewmodel.WeatherUiState
import java.util.Locale
import kotlin.math.roundToInt

// Popular cities to show before any search
private val popularCities = listOf("Tokyo", "New York", "Seoul", "Bangkok", "Hong Kong")

@Composable
fun WeatherListScreen(
    uiState: WeatherUiState,
    searchedCities: List<String>,
    onSearch: (String) -> Unit,
    onCitySelected: (String) -> Unit
) {
    var searchQuery by rememberSaveable { mutableStateOf("") }

    LazyColumn(
        modifier = mainscreenBackgroundModifier()
    ) {
        // Search Bar with Button
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                SearchBar(
                    query = searchQuery,
                    onQueryChange = { searchQuery = it },
                    modifier = Modifier.weight(1f)
                )
                Button(
                    onClick = {
                        if (searchQuery.isNotBlank()) {
                            onSearch(searchQuery)
                            searchQuery = ""
                        }
                    },
                    modifier = Modifier.height(56.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search city"
                    )
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Content based on UI state
        when (uiState) {
            is WeatherUiState.Loading -> {
                item {
                    LoadingContent()
                }
            }
            is WeatherUiState.Success -> {
                if (searchedCities.size < 2) {
                    // Show popular cities when no search has been made
                    item {
                        PopularCitiesContent(
                            onCitySelected = onCitySelected
                        )
                    }
                } else {
                    // Show the current weather result card
                    item {
                        WeatherResultCard(
                            weather = uiState.weather,
                            onClick = { onCitySelected(uiState.weather.name) }
                        )
                    }

                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                    }

                    // Show search history if there are previous searches
                    if (searchedCities.size > 1) {
                        item {
                            Text(
                                text = "Search History",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onBackground,
                                modifier = Modifier.padding(vertical = 8.dp)
                            )
                        }
                        items(searchedCities.filter { it != uiState.weather.name }) { city ->
                            CityHistoryItem(
                                cityName = city,
                                onClick = { onCitySelected(city) }
                            )
                        }
                        item {
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }
            }
            is WeatherUiState.Error -> {
                item {
                    ErrorContent(message = uiState.message)
                }
            }
        }
    }
}

@Composable
private fun PopularCitiesContent(
    onCitySelected: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "Most Searched Cities",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        Text(
            text = "Tap any city to view its weather",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        popularCities.forEach { city ->
            PopularCityCard(
                cityName = city,
                onClick = { onCitySelected(city) }
            )
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
private fun PopularCityCard(
    cityName: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = cityName,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = "View Weather →",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
private fun LoadingContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(48.dp),
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Searching for city...",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
            )
        }
    }
}

@Composable
private fun ErrorContent(message: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(32.dp)
        ) {
            Text(
                text = "⚠️",
                fontSize = 64.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Search Failed",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.error
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = message,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                textAlign = TextAlign.Center
            )
        }
    }
}

////////////////////// RESULT CARD
@Composable
private fun WeatherResultCard(
    weather: OpenWeatherResponse,
    onClick: () -> Unit
) {
    val isDay = isDaytime(weather)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isDay) {
                MaterialTheme.colorScheme.surfaceVariant
            } else {
                MaterialTheme.colorScheme.onBackground
            }
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Location and Temperature
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = weather.name,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isDay){
                            MaterialTheme.colorScheme.onSurfaceVariant
                        } else{
                            MaterialTheme.colorScheme.onPrimary
                        }
                    )
                    Text(
                        text = weather.sys.country,
                        fontSize = 14.sp,
                        color = if (isDay){
                            MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                        } else{
                            MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f)
                        }
                    )
                }

                val tempCelsius = (weather.main.temp - 273.15).roundToInt()
                Text(
                    text = "${tempCelsius}°C",
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // CONDITION W/ ICON
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val weatherIcon = getWeatherIcon(
                    weather.weather.firstOrNull()?.main ?: "Clear",
                    isDay
                )
                Icon(
                    imageVector = weatherIcon,
                    contentDescription = "Weather condition",
                    modifier = Modifier.size(32.dp),
                    tint = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.size(8.dp))

                Text(
                    text = weather.weather.firstOrNull()?.description?.replaceFirstChar {
                        if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString()
                    } ?: "Unknown",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = if (isDay){
                        MaterialTheme.colorScheme.onSurfaceVariant
                    } else{
                        MaterialTheme.colorScheme.onPrimary
                    }
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Additional Info
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                WeatherInfoItem(
                    label = "Humidity",
                    value = "${weather.main.humidity}%",
                    isDay = isDay
                )
                WeatherInfoItem(
                    label = "Sunrise",
                    value = formatTime(weather.sys.sunrise, weather.timezone),
                    isDay = isDay
                )
                WeatherInfoItem(
                    label = "Sunset",
                    value = formatTime(weather.sys.sunset, weather.timezone),
                    isDay = isDay
                )
            }
        }
    }
}
// ADDITIONAL INFO PART
@Composable
private fun WeatherInfoItem(label: String, value: String, isDay: Boolean) {
    Column(
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = label,
            fontSize = 12.sp,
            color = if (isDay){
                MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
            } else{
                MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.6f)
            }
        )
        Text(
            text = value,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = if (isDay){
                MaterialTheme.colorScheme.onSurfaceVariant
            } else{
                MaterialTheme.colorScheme.onPrimary
            }
        )
    }
}

@Composable
private fun CityHistoryItem(
    cityName: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = cityName,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "Tap to view",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
        }
    }
}
////////////////////////////////////////////// Previews //////////////////////////////////////////////

@Preview(showBackground = true)
@Composable
private fun WeatherListScreenLoadingPreview() {
    WeatherAppAndroidComposeTheme {
        WeatherListScreen(
            uiState = WeatherUiState.Loading,
            searchedCities = emptyList(),
            onSearch = {},
            onCitySelected = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun WeatherListScreenEmptyPreview() {
    WeatherAppAndroidComposeTheme {
        WeatherListScreen(
            uiState = WeatherUiState.Loading,
            searchedCities = emptyList(),
            onSearch = {},
            onCitySelected = {}
        )
    }
}
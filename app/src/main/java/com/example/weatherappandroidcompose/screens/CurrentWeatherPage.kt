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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.roundToInt

@Composable
fun CurrentWeatherScreen(
    uiState: WeatherUiState,
    selectedCity: String
) {
    Box(
        modifier = mainscreenBackgroundModifier()
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        when (uiState) {
            is WeatherUiState.Loading -> {
                LoadingState()
            }
            is WeatherUiState.Success -> {
                CurrentWeatherContent(weather = uiState.weather)
            }
            is WeatherUiState.Error -> {
                ErrorState(message = uiState.message)
            }
        }
    }
}

@Composable
private fun LoadingState() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(64.dp),
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Loading weather data...",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
        )
    }
}

@Composable
private fun ErrorState(message: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.padding(32.dp)
    ) {
        Text(
            text = "⚠️",
            fontSize = 64.sp
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Error",
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

@Composable
private fun CurrentWeatherContent(weather: OpenWeatherResponse) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        // Location Info
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = weather.name,
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = weather.sys.country,
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
            )
        }

        // Weather Icon
        Icon(
            imageVector = Icons.Default.WbSunny,
            contentDescription = "Weather condition",
            modifier = Modifier.size(120.dp),
            tint = MaterialTheme.colorScheme.primary
        )

        // Temperature (Convert from Kelvin to Celsius)
        val tempCelsius = (weather.main.temp - 273.15).roundToInt()
        Text(
            text = "${tempCelsius}°C",
            fontSize = 72.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        // Condition
        Text(
            text = weather.weather.firstOrNull()?.description?.replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString()
            } ?: "Unknown",
            fontSize = 28.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onBackground
        )

        // Feels Like
        val feelsLikeCelsius = (weather.main.feelsLike - 273.15).roundToInt()
        Text(
            text = "Feels like ${feelsLikeCelsius}°C",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Weather Details Card
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
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                WeatherDetailItem(
                    label = "Humidity",
                    value = "${weather.main.humidity}%"
                )
                WeatherDetailItem(
                    label = "Sunrise",
                    value = formatTime(weather.sys.sunrise)
                )
                WeatherDetailItem(
                    label = "Sunset",
                    value = formatTime(weather.sys.sunset)
                )
            }
        }
    }
}

@Composable
private fun WeatherDetailItem(label: String, value: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = label,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = value,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

// Helper function to format Unix timestamp to time
private fun formatTime(timestamp: Long): String {
    val date = Date(timestamp * 1000)
    val format = SimpleDateFormat("h:mm a", Locale.getDefault())
    return format.format(date)
}

////////////////////////////////////////////// Previews //////////////////////////////////////////////

@Preview(showBackground = true)
@Composable
private fun CurrentWeatherScreenLoadingPreview() {
    WeatherAppAndroidComposeTheme {
        CurrentWeatherScreen(
            uiState = WeatherUiState.Loading,
            selectedCity = "Manila"
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CurrentWeatherScreenErrorPreview() {
    WeatherAppAndroidComposeTheme {
        CurrentWeatherScreen(
            uiState = WeatherUiState.Error("City not found. Please check spelling."),
            selectedCity = "Manila"
        )
    }
}
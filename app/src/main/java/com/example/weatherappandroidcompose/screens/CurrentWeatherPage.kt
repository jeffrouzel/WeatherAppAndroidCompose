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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherappandroidcompose.data.WeatherCity
import com.example.weatherappandroidcompose.ui.theme.WeatherAppAndroidComposeTheme
import com.example.weatherappandroidcompose.ui.theme.mainscreenBackgroundModifier

@Composable
fun CurrentWeatherScreen(
    selectedCity: WeatherCity?
) {
    Box(
        modifier = mainscreenBackgroundModifier()
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        if (selectedCity != null) {
            CurrentWeatherContent(city = selectedCity)
        } else {
            Text(
                text = "No city selected",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
            )
        }
    }
}

@Composable
private fun CurrentWeatherContent(city: WeatherCity) {
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
                text = city.cityName,
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = city.country,
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

        // Temperature
        Text(
            text = "${city.temperature}°C",
            fontSize = 72.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        // Condition
        Text(
            text = city.condition,
            fontSize = 28.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onBackground
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
                    value = "${city.humidity}%"
                )
                WeatherDetailItem(
                    label = "Sunrise",
                    value = "${city.sunrise} am"
                )
                WeatherDetailItem(
                    label = "Sunset",
                    value = "${city.sunset} pm"
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

////////////////////////////////////////////// Previews //////////////////////////////////////////////

@Preview(showBackground = true)
@Composable
private fun CurrentWeatherScreenPreview() {
    WeatherAppAndroidComposeTheme {
        CurrentWeatherScreen(
            selectedCity = WeatherCity(
                id = 1,
                cityName = "Manila",
                country = "Philippines",
                temperature = 32,
                condition = "Sunny",
                humidity = 75,
                sunrise = "5:00",
                sunset = "6:00"
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CurrentWeatherScreenNoDataPreview() {
    WeatherAppAndroidComposeTheme {
        CurrentWeatherScreen(selectedCity = null)
    }
}
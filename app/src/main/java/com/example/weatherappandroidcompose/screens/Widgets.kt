package com.example.weatherappandroidcompose.screens

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AcUnit
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.CloudQueue
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Thunderstorm
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.filled.WbCloudy
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.weatherappandroidcompose.R
import com.example.weatherappandroidcompose.api.OpenWeatherResponse
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = query,
        onValueChange = onQueryChange,
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        trailingIcon = {
            if (query.isNotEmpty()) {
                IconButton(onClick = { onQueryChange("") }) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = "Clear search",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
        ),
        placeholder = {
            Text(
                text = stringResource(R.string.placeholder_search),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
            )
        },
        shape = RoundedCornerShape(32.dp),
        singleLine = true,                                             // restricts the text field to one line only
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 56.dp)
    )
}

// TIME FORMATTER
fun formatTime(timestamp: Long, timezoneOffset: Int): String {
    val date = Date(timestamp * 1000)
    val format = SimpleDateFormat("h:mm a", Locale.getDefault())

    // Create a timezone with the offset from the API (in seconds)
    val timezone = TimeZone.getTimeZone("GMT")
    timezone.rawOffset = timezoneOffset * 1000 // Convert seconds to milliseconds
    format.timeZone = timezone

    return format.format(date)
}

@Composable
fun getWeatherIcon(condition: String, isDaytime: Boolean = true) = when (condition.lowercase()) {
    "clear" -> if (isDaytime) Icons.Default.WbSunny else Icons.Default.DarkMode
    "clouds" -> Icons.Default.WbCloudy
    "rain", "drizzle" -> Icons.Default.WaterDrop
    "thunderstorm" -> Icons.Default.Thunderstorm
    "snow" -> Icons.Default.AcUnit
    "mist", "fog", "haze", "smoke", "dust", "sand", "ash" -> Icons.Default.Cloud
    else -> Icons.Default.CloudQueue // Default for unknown conditions
}

// DAY OR NIGHT CHECKER
fun isDaytime(currentTime: Long, sunrise: Long, sunset: Long): Boolean {
    return currentTime in sunrise..sunset
}

fun isDaytime(weather: OpenWeatherResponse): Boolean {
    return isDaytime(weather.dt, weather.sys.sunrise, weather.sys.sunset)
}

////////////////////////////////////////////// Previews //////////////////////////////////////////////
@Preview(showBackground = true)
@Composable
fun SearchBarPreview() {
    SearchBar(
        query = "",
        onQueryChange = {},
        modifier = Modifier.padding(16.dp)
    )
}
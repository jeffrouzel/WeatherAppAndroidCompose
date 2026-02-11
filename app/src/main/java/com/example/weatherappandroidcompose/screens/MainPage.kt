package com.example.weatherappandroidcompose.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ViewList
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.weatherappandroidcompose.R
import com.example.weatherappandroidcompose.api.NetworkResponse
import com.example.weatherappandroidcompose.ui.theme.WeatherAppAndroidComposeTheme
import com.example.weatherappandroidcompose.ui.theme.mainscreenBackgroundModifier

@Composable
fun MainScreen(viewModel: WeatherViewModel = viewModel()) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            BottomNavBar(
                currentRoute = currentRoute,
                onCurrentClick = {
                    navController.navigate("current_weather") {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                onListClick = {
                    navController.navigate("weather_list") {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = "current_weather",
            modifier = Modifier.padding(paddingValues)
        ) {
            composable("current_weather") {
                CurrentWeatherScreen(viewModel)
            }
            composable("weather_list") {
                WeatherListScreen(viewModel)
            }
        }
    }
}

@Composable
fun CurrentWeatherScreen(viewModel: WeatherViewModel) {
    val weatherData by viewModel.weatherData.observeAsState()

    Box(
        modifier = mainscreenBackgroundModifier(),
        contentAlignment = Alignment.Center
    ) {
        when (val result = weatherData) {
            is NetworkResponse.Success -> {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = result.data.location.name, style = MaterialTheme.typography.headlineMedium)
                    Text(text = "${result.data.current.temp_c}°C", style = MaterialTheme.typography.displayLarge)
                    Text(text = result.data.current.condition.text, style = MaterialTheme.typography.bodyLarge)
                }
            }
            is NetworkResponse.Error -> {
                Text(text = result.message, color = MaterialTheme.colorScheme.error)
            }
            is NetworkResponse.Loading -> {
                CircularProgressIndicator()
            }
            null -> {
                Text(text = "Current Weather")
            }
        }
    }
}

@Composable
private fun BottomNavBar(
    currentRoute: String?,
    onCurrentClick: () -> Unit,
    onListClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.primary,
        modifier = modifier
    ) {
        // Current Weather
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.WbSunny,
                    contentDescription = null
                )
            },
            label = {
                Text(text = stringResource(R.string.bottom_nav_current))
            },
            selected = currentRoute == "current_weather",
            onClick = onCurrentClick,
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                selectedTextColor = MaterialTheme.colorScheme.onPrimary,
                unselectedIconColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.6f),
                unselectedTextColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.6f),
                indicatorColor = MaterialTheme.colorScheme.primaryContainer
            )
        )

        // Divider
        VerticalDivider(
            modifier = Modifier
                .height(36.dp)
                .align(Alignment.CenterVertically),
            thickness = 2.dp,
            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.3f)
        )
        // Weather List
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ViewList,
                    contentDescription = null
                )
            },
            label = {
                Text(text = stringResource(R.string.bottom_nav_list))
            },
            selected = currentRoute == "weather_list",
            onClick = onListClick,
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                selectedTextColor = MaterialTheme.colorScheme.onPrimary,
                unselectedIconColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.6f),
                unselectedTextColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.6f),
                indicatorColor = MaterialTheme.colorScheme.primaryContainer
            )
        )
    }
}

@Preview
@Composable
private fun BottomNavBarPreview() {
    WeatherAppAndroidComposeTheme {
        BottomNavBar(
            currentRoute = "current_weather",
            onCurrentClick = {},
            onListClick = {}
        )
    }
}

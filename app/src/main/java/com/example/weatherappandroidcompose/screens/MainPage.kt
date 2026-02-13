package com.example.weatherappandroidcompose.screens

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ViewList
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.weatherappandroidcompose.R
import com.example.weatherappandroidcompose.viewmodel.WeatherViewModel

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val viewModel: WeatherViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()

    var searchedCities by rememberSaveable { mutableStateOf<List<String>>(listOf("Manila")) }
    var selectedCity by rememberSaveable { mutableStateOf("Manila") }

    // Fetch weather whenever selectedCity changes
    LaunchedEffect(selectedCity) {
        viewModel.fetchWeatherByCity(selectedCity)
    }

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
                CurrentWeatherScreen(
                    uiState = uiState,
                )
            }
            composable("weather_list") {
                WeatherListScreen(
                    uiState = uiState,
                    searchedCities = searchedCities,
                    onSearch = { city ->
                        selectedCity = city
                    },
                    onCitySelected = { city ->
                        // Add city to search history if clicked
                        if (!searchedCities.contains(city)) {
                            searchedCities = searchedCities + city
                        }
                        selectedCity = city
                        navController.navigate("current_weather") {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
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

        VerticalDivider(
            modifier = Modifier
                .height(36.dp)
                .align(Alignment.CenterVertically),
            thickness = 2.dp,
            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.3f)
        )

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
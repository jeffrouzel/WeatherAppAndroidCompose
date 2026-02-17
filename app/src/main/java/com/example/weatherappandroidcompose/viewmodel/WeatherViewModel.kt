package com.example.weatherappandroidcompose.viewmodel

import androidx.lifecycle.ViewModel
import com.example.weatherappandroidcompose.api.OpenWeatherResponse
import com.example.weatherappandroidcompose.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

sealed interface WeatherUiState {
    data object Loading : WeatherUiState
    data class Error(val message: String) : WeatherUiState
    data class Success(val weather: OpenWeatherResponse) : WeatherUiState
}

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<WeatherUiState>(WeatherUiState.Loading)
    val uiState: StateFlow<WeatherUiState> = _uiState

    private val _cityHistory = MutableStateFlow<List<String>>(listOf("Manila"))
    val cityHistory: StateFlow<List<String>> = _cityHistory

    fun addCityToHistory(city: String) {
        if (!_cityHistory.value.contains(city)) {
            _cityHistory.value = _cityHistory.value + city
        }
    }

    fun fetchWeatherByCity(city: String) {
        if (city.isBlank()) {
            _uiState.value = WeatherUiState.Error("Please enter a city name")
            return
        }

        _uiState.value = WeatherUiState.Loading

        repository.getWeatherByCity(
            city = city.trim(),
            onSuccess = { weather ->
                _uiState.value = WeatherUiState.Success(weather)
            },
            onError = { errorMessage ->
                _uiState.value = WeatherUiState.Error(errorMessage)
            }
        )
    }
}
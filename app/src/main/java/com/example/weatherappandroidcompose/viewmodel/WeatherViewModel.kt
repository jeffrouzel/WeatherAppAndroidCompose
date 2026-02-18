package com.example.weatherappandroidcompose.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherappandroidcompose.api.OpenWeatherResponse
import com.example.weatherappandroidcompose.repository.CityDataStore
import com.example.weatherappandroidcompose.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface WeatherUiState {
    data object Loading : WeatherUiState
    data class Error(val message: String) : WeatherUiState
    data class Success(val weather: OpenWeatherResponse) : WeatherUiState
}

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository,
    private val cityDataStore: CityDataStore
) : ViewModel() {

    private val _uiState = MutableStateFlow<WeatherUiState>(WeatherUiState.Loading)
    val uiState: StateFlow<WeatherUiState> = _uiState

    fun fetchWeather(city: String) {
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

    val cityHistory: StateFlow<List<String>> = cityDataStore.cityHistory
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = listOf("Manila")
        )

    fun addCityToHistory(city: String) {
        viewModelScope.launch {
            cityDataStore.addCity(city)
        }
    }

    fun clearCityHistory() {
        viewModelScope.launch {
            cityDataStore.clearHistory()
        }
    }
}
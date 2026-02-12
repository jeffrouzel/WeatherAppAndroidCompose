package com.example.weatherappandroidcompose.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import com.example.weatherappandroidcompose.ui.theme.mainscreenBackgroundModifier

@Composable
fun CurrentWeatherScreen() {
    Box(
        modifier = mainscreenBackgroundModifier(),
        contentAlignment = Alignment.Center
    ) {
        Text("Current Weather Screen")
    }
}
package com.example.weatherappandroidcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.weatherappandroidcompose.navigation.NavPath
import com.example.weatherappandroidcompose.ui.theme.WeatherAppAndroidComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeatherAppAndroidComposeTheme{
                NavPath()
            }
        }
    }
}

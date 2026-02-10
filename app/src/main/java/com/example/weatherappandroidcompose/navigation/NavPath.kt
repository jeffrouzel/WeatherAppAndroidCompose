package com.example.weatherappandroidcompose.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.weatherappandroidcompose.screens.LandingScreen
import com.example.weatherappandroidcompose.screens.MainScreen

@Composable
fun NavPath(navController: NavHostController = rememberNavController()){
    NavHost(navController = navController, startDestination = "landing"){
        composable("landing"){
            LandingScreen(
                onGetStarted = {
                    navController.navigate("main") {
                        popUpTo("landing") { inclusive = true }
                    }
                }
            )
        }

        composable("main"){MainScreen()}

    }

}
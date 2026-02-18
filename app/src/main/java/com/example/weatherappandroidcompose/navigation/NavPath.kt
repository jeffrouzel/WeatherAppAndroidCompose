package com.example.weatherappandroidcompose.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
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
        composable(
            route = "landing",
            exitTransition = {
                slideOutHorizontally(targetOffsetX = { -it }, animationSpec = tween(400)) +
                        fadeOut(animationSpec = tween(400))
            }
        ){
            LandingScreen(
                onGetStarted = {
                    navController.navigate("main") {
                        popUpTo("landing") { inclusive = true }
                    }
                }
            )
        }

        composable(
            route = "main",
            enterTransition = {
                slideInHorizontally(initialOffsetX = { it }, animationSpec = tween(400)) +
                        fadeIn(animationSpec = tween(400))
            }
        ){MainScreen()}

    }

}
package com.example.weatherappandroidcompose.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


// Landing Screen Page
val landingColumnModifier = Modifier
    .background(Peach80)
    .fillMaxSize()
    .padding(16.dp)

val landingImageModifier = Modifier
    .fillMaxWidth()

val landingButtonModifier = Modifier
    .fillMaxWidth()
    .padding(horizontal = 16.dp)
    .height(56.dp)

// Main Screen
@Composable
fun mainscreenBackgroundModifier(): Modifier  = Modifier
    .fillMaxSize()
    .background(MaterialTheme.colorScheme.background)

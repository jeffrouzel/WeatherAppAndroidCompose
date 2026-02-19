package com.example.weatherappandroidcompose.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val landingTitleHalfOne = TextStyle(
    color = Orange40,
    fontSize = 36.sp,
    fontWeight = FontWeight.Bold,
    shadow = Shadow(
        color = Color.Gray,
        offset = Offset(4f, 4f),
        blurRadius = 8f
    ),

)

val landingTitleHalfTwo = TextStyle(
    color = White,
    fontSize = 36.sp,
    fontWeight = FontWeight.Bold,
    shadow = Shadow(
        color = Color.Gray,
        offset = Offset(4f, 4f),
        blurRadius = 8f
    ),
)

val landingTextTextStyle = TextStyle(
    color = Orange40,
    fontSize = 16.sp,
    fontWeight = FontWeight.Bold,
    shadow = Shadow(
        color = Color.Gray,
        offset = Offset(2f, 2f),
        blurRadius = 8f
    )
)

val landingButtonTextStyle = TextStyle(
    color = White,
    fontSize = 16.sp,
    fontWeight = FontWeight.Bold,
    shadow = Shadow(
        color = Color.Gray,
        offset = Offset(2f, 2f),
        blurRadius = 4f
    )
)

@Composable
fun daynightColorAlpha(isDay: Boolean): Color {
    return if (isDay){
        MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
    } else{
        MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f)
    }
}

@Composable
fun daynightColor(isDay: Boolean): Color {
    return if (isDay){
        MaterialTheme.colorScheme.onSurfaceVariant
    } else{
        MaterialTheme.colorScheme.onPrimary
    }
}
package com.example.weatherappandroidcompose.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.weatherappandroidcompose.R
import com.example.weatherappandroidcompose.ui.theme.landingButtonModifier
import com.example.weatherappandroidcompose.ui.theme.landingButtonTextStyle
import com.example.weatherappandroidcompose.ui.theme.landingColumnModifier
import com.example.weatherappandroidcompose.ui.theme.landingImageModifier
import com.example.weatherappandroidcompose.ui.theme.landingTextTextStyle
import com.example.weatherappandroidcompose.ui.theme.landingTitleHalfOne
import com.example.weatherappandroidcompose.ui.theme.landingTitleHalfTwo

@Composable
fun LandingScreen(onGetStarted: () -> Unit, modifier: Modifier = Modifier){
    Column (
        modifier = landingColumnModifier,
        verticalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
        ) {
        Text("Welcome to the", style = landingTextTextStyle)
        Image(
            painter = painterResource(id = R.drawable.landing_sun),
            contentDescription = null,
            modifier = landingImageModifier,
            contentScale = ContentScale.Crop
        )
        AppTitleLanding()
        Button(onClick = onGetStarted, modifier = landingButtonModifier) {
            Text("Open", style = landingButtonTextStyle)
        }
    }
}

@Composable
fun AppTitleLanding(modifier: Modifier = Modifier){
    Row {
        Text("Weather", style = landingTitleHalfOne)
        Text("App", style = landingTitleHalfTwo)
    }
}

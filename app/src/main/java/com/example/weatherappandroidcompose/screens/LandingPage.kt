package com.example.weatherappandroidcompose.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.weatherappandroidcompose.R
import com.example.weatherappandroidcompose.ui.theme.landingColumnModifier
import com.example.weatherappandroidcompose.ui.theme.landingImageModifier

@Preview(showBackground = true)
@Composable
fun LandingScreen(modifier: Modifier = Modifier){
    Column (
        modifier = landingColumnModifier,
        verticalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
        ) {
        Image(
            painter = painterResource(id = R.drawable.landing_sun),
            contentDescription = null,
            modifier = landingImageModifier,
            contentScale = ContentScale.Crop
        )
        Text("Welcome to")
        AppTitleLanding()
        Button(onClick = { /*TODO*/ }) {
            Text("Open")
        }
    }
}

@Composable
fun AppTitleLanding(modifier: Modifier = Modifier){
    Text("Weather")
    Text("App")
}

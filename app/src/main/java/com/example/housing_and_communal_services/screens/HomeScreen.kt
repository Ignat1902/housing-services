package com.example.housing_and_communal_services.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

@Composable
fun HomeScreen() {
    Text(
        text = "Это главная страница",
        modifier = Modifier
            .fillMaxSize(),
        textAlign = TextAlign.Center
    )
}
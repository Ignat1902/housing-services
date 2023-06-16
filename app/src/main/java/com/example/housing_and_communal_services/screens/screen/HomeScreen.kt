package com.example.housing_and_communal_services.screens.screen

import HomeCard
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun HomeScreen() {
   Column(Modifier.fillMaxSize()){
      HomeCard(flat = "257", city = "Москва", street = "Маяковского", home = "21", balance = "0")
   }

}
package com.example.housing_and_communal_services.screens.bottom_bar_screen

import HomeCard
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.housing_and_communal_services.data.models.User
import com.example.housing_and_communal_services.data.repositories.MeteringDeviceRepository
import kotlinx.coroutines.launch

@Composable
fun HomeScreen() {
   Column(Modifier.fillMaxSize()){
      var user by remember { mutableStateOf<User?>(null) }
      val coroutineScope = rememberCoroutineScope()

      LaunchedEffect(Unit) {
         coroutineScope.launch {
            val authRepository = MeteringDeviceRepository()
            user = authRepository.fetchUserById()
         }
      }
      user?.let { HomeCard(it) }
   }

}
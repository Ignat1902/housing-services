package com.example.housing_and_communal_services.screens.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.housing_and_communal_services.R
import com.example.housing_and_communal_services.showBars
import kotlinx.coroutines.delay

@Composable
fun StartScreen(navController: NavController) {
    showBars(flag = false)

    LaunchedEffect(key1 = true){
        delay(2500L)
        navController.navigate("auth"){
            popUpTo(0)
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(id = R.drawable.apartment_48px),
            contentDescription = "Logo",
            tint = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.size(196.dp)
        )
        Spacer(Modifier.height(24.dp))
        Greeting(name = "ЖКХ\nВашего Дома")
    }
}
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = name,
        modifier = modifier,
        style = MaterialTheme.typography.displayMedium,
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.SemiBold,
        color = MaterialTheme.colorScheme.onBackground
    )
}



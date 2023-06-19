package com.example.housing_and_communal_services.screens.authorization

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.housing_and_communal_services.R
import com.example.housing_and_communal_services.data.repositories.AuthRepository
import com.example.housing_and_communal_services.data.repositories.FirebaseRequest
import com.example.housing_and_communal_services.navigation.Screen
import com.example.housing_and_communal_services.view_models.LoginViewModel

@Composable
fun WelcomeScreen(
    navController: NavController,
    loginViewModel: LoginViewModel? = null,
    onNavToHomePage: () -> Unit,
) {
    val request = FirebaseRequest()
    val authRepository = AuthRepository()
    val loginUiState = loginViewModel?.loginUiState
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 48.dp)
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(id = R.drawable.apartment_48px),
                contentDescription = "Logo",
                tint = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.size(98.dp)
            )
            Spacer(Modifier.height(24.dp))
            Text(
                text = Screen.Welcome.title,
                style = MaterialTheme.typography.displaySmall,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        Column() {
            Button(
                onClick = {
                    navController.navigate(Screen.Login.route) {
                        launchSingleTop = true
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(top = 24.dp),
                shape = RoundedCornerShape(4.dp),
            ) {
                Text(
                    text = "Вход",
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.titleLarge,
                )
            }

            Button(
                onClick = {
                    navController.navigate(Screen.AccountNumberEntryPage.route) {
                        launchSingleTop = true
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(top = 24.dp),
                shape = RoundedCornerShape(4.dp),

                ) {
                Text(
                    text = "Регистрация",
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.titleLarge,
                )
            }
        }

    }

    if (loginUiState?.isLoading == true) {
        CircularProgressIndicator()
    }
    val load = remember{
        mutableStateOf(false)
    }

    if (load.value){
        Box(
            modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.tertiary),
            contentAlignment = Alignment.Center
        ){
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.onTertiary
            )
        }
    }

    LaunchedEffect(key1 = loginViewModel?.hasUser) {
        if (loginViewModel?.hasUser == true) {
            load.value = true
            request.isDocumentExists("User", authRepository.getUserId()) { exists ->
                if (exists) {
                    load.value = false
                    onNavToHomePage.invoke()
                }else
                    load.value = false
            }
        }
    }
}
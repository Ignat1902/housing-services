package com.example.housing_and_communal_services.screens.authorization.login

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.housing_and_communal_services.navigation.Screen
import com.example.housing_and_communal_services.showBars
import com.example.housing_and_communal_services.view_models.LoginViewModel

@Composable
fun ForgotPasswordScreen(viewModel: LoginViewModel, onNavToHomePage: () -> Unit) {
    var email by remember { mutableStateOf("") }
    val toastMessage by viewModel.toastMessage.observeAsState()
    val context = LocalContext.current
    showBars(flag = true)
    val loginUiState = viewModel.loginUiState
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            Text(
                text = Screen.ForgotPassword.title,
                modifier = Modifier.padding(top = 24.dp),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                "Введите адресс электронной почты, на которую будет отправленна ссылка для смены пароля",
                modifier = Modifier.padding(top = 24.dp),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.tertiary
            )
        }
        OutlinedTextField(
            value = email,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            singleLine = true,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = {
                viewModel.sendPasswordResetEmail(email)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .padding(horizontal = 24.dp),
            shape = RoundedCornerShape(4.dp),

            ) {
            Text(
                text = "Отправить",
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.titleLarge
            )
        }

        LaunchedEffect(toastMessage) {
            toastMessage?.let { message ->
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            }
        }

        if (loginUiState.isLoading) {
            CircularProgressIndicator()
        }

        LaunchedEffect(key1 = viewModel.hasUser) {
            if (viewModel.hasUser) {
                onNavToHomePage.invoke()
            }
        }

    }
}
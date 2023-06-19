package com.example.housing_and_communal_services.screens.authorization.login

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.housing_and_communal_services.R
import com.example.housing_and_communal_services.data.repositories.AuthRepository
import com.example.housing_and_communal_services.data.repositories.FirebaseRequest
import com.example.housing_and_communal_services.showBars
import com.example.housing_and_communal_services.view_models.LoginViewModel

@Composable
fun LoginPage(
    loginViewModel: LoginViewModel? = null,
    onForgotPass: () -> Unit,
    onNavToHomePage: () -> Unit,
    onNavToSignUpTelephone: () -> Unit,
    onNavToSignUpPage: () -> Unit
) {
    showBars(flag = true)

    val request = FirebaseRequest()
    val authRepository = AuthRepository()

    val loginUiState = loginViewModel?.loginUiState
    val isError = loginUiState?.loginError != null
    val context = LocalContext.current

    val passwordVisibility = remember { mutableStateOf(false) }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 32.dp)

    ) {
        Text(
            stringResource(R.string.authorization),
            style = MaterialTheme.typography.displayMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
        if (isError) {
            Text(
                text = loginUiState?.loginError ?: "Неизвестная ошибка",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.error
            )
        }
        Spacer(Modifier.height(96.dp))

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.verticalScroll(rememberScrollState())
        ) {
            OutlinedTextField(
                value = loginUiState?.login ?: "",
                onValueChange = {
                    loginViewModel?.onLoginChange(it)
                },
                label = { Text(text = "Логин") },
                placeholder = { Text(text = "Введите логин") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier
                    .fillMaxWidth(),
                isError = isError
            )
            Spacer(Modifier.height(24.dp))

            //Пароль
            OutlinedTextField(
                value = loginUiState?.password ?: "",
                trailingIcon = {
                    IconButton(onClick = { passwordVisibility.value = !passwordVisibility.value }) {
                        Icon(
                            painter = painterResource(id = R.drawable.password_eye),
                            contentDescription = "eye",
                            tint = if (passwordVisibility.value) MaterialTheme.colorScheme.primary else Color.Gray
                        )
                    }
                },
                onValueChange = {
                    loginViewModel?.onPasswordNameChange(it)
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                label = { Text(text = "Пароль") },
                placeholder = { Text(text = "Введите пароль") },
                singleLine = true,
                isError = isError,
                supportingText = {
                    Text(
                        text = "Забыли пароль?",
                        textDecoration = TextDecoration.Underline,
                        modifier = Modifier.clickable {
                            onForgotPass.invoke()
                        }
                    )
                },
                modifier = Modifier
                    .fillMaxWidth(),
                visualTransformation = if (passwordVisibility.value) VisualTransformation.None else PasswordVisualTransformation(),
            )
            Spacer(Modifier.height(48.dp))
            Button(
                onClick = {
                    loginViewModel?.loginUser(context)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .padding(horizontal = 24.dp),
                shape = RoundedCornerShape(4.dp),

                ) {
                Text(
                    text = stringResource(R.string.sign_in),
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.titleLarge
                )
            }

            Text(
                text = "Нет аккаунта?",
                textDecoration = TextDecoration.Underline,
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier
                    .padding(top = 10.dp)
                    .clickable {
                        onNavToSignUpPage.invoke()
                    }
            )
            Spacer(modifier = Modifier.height(24.dp))
            Divider(modifier = Modifier.padding(horizontal = 16.dp))
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = {
                    onNavToSignUpTelephone.invoke()
                },
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(4.dp),

                ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.Phone,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSecondary
                    )
                    Text(
                        "Войти с помощью телефона",
                        modifier = Modifier.padding(horizontal = 12.dp)
                    )
                }
            }

        }
        if (loginUiState?.isLoading == true) {
            CircularProgressIndicator()
        }


        LaunchedEffect(key1 = loginViewModel?.hasUser) {
            if (loginViewModel?.hasUser == true) {
                request.isDocumentExists("User", authRepository.getUserId()) { exists ->
                    if (exists) {
                        onNavToHomePage.invoke()
                    }
                }
            }
        }

    }
}
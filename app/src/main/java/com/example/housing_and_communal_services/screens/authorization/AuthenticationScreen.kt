package com.example.housing_and_communal_services.screens.authorization

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.Housing_and_communal_servicesTheme
import com.example.housing_and_communal_services.R
import com.example.housing_and_communal_services.showBars

@Composable
fun LoginPage(
    loginViewModel: LoginViewModel? = null,
    onNavToHomePage: () -> Unit,
    onNavToSignUpPage: () -> Unit
) {
    showBars(flag = true)
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
                value = loginUiState?.userName ?: "",
                onValueChange = {
                    loginViewModel?.onUserNameChange(it)
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

        }
        if (loginUiState?.isLoading == true){
            CircularProgressIndicator()
        }

        LaunchedEffect(key1 = loginViewModel?.hasUser){
            if (loginViewModel?.hasUser == true){
                onNavToHomePage.invoke()
            }
        }

    }
}

@Preview(showSystemUi = true)
@Composable
fun PrevLoginScreen() {
    Housing_and_communal_servicesTheme() {
        LoginPage(onNavToHomePage = { /*TODO*/ }) {

        }
    }

}
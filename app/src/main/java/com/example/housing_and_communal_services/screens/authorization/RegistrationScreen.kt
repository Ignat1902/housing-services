package com.example.housing_and_communal_services.screens.authorization

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.*
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
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
import com.example.housing_and_communal_services.showBars

@Composable
fun RegistrationPage(
    loginViewModel: LoginViewModel? = null,
    onNavToHomePage: () -> Unit,
    onNavToLoginPage: () -> Unit
) {
    val loginUiState = loginViewModel?.loginUiState
    val isError = loginUiState?.signUpError != null
    val context = LocalContext.current
    val passwordVisibility = remember { mutableStateOf(false) }
    showBars(flag = true)

    val scrollState = rememberScrollState()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp),
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 32.dp)
            .verticalScroll(scrollState)
    ) {
        Text(
            stringResource(R.string.registration_name),
            modifier = Modifier.padding(top = 24.dp),
            style = MaterialTheme.typography.displayMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
        if (isError) {
            Text(
                text = loginUiState?.signUpError ?: "Неизвестная ошибка",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.error
            )
        }


        //"Электронная почта"
        OutlinedTextField(
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            value = loginUiState?.userNameSignUp ?: "",
            trailingIcon = {
                IconButton(onClick = { TODO() }) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_info_24),
                        contentDescription = "info",
                    )
                }
            },
            onValueChange = {
                loginViewModel?.onUserNameChangeSignUp(it)
            },
            label = { Text(text = "Email") },
            isError = isError,
            placeholder = {
                Text(
                    text = "Введите email адрес",
                    maxLines = 1
                )
            },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
        )


        //Пароль
        OutlinedTextField(
            value = loginUiState?.passwordSignUp ?: "",
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
                loginViewModel?.onPasswordChangeSignUp(it)
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            label = { Text(text = "Пароль") },
            supportingText = { Text(text = "Введите не менее 6 символов") },
            placeholder = { Text(text = "Введите пароль") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth(),
            visualTransformation = if (passwordVisibility.value) VisualTransformation.None else PasswordVisualTransformation(),
        )

        //Подтверждение пароля
        OutlinedTextField(
            value = loginUiState?.confirmPasswordSignUp ?: "",
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
                loginViewModel?.onConfirmPasswordChangeSignUp(it)
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            label = { Text(text = "Пароль") },
            supportingText = { Text(text = "Введите не менее 6 символов") },
            placeholder = { Text(text = "Введите пароль") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth(),
            visualTransformation = if (passwordVisibility.value) VisualTransformation.None else PasswordVisualTransformation(),
        )

        //Кнопка регистрации
        Button(
            onClick = {
                loginViewModel?.createUser(context)
            },
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(top = 24.dp),
            shape = RoundedCornerShape(4.dp),

            ) {
            Text(
                text = stringResource(R.string.register),
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding()
            )
        }
        Text(
            text = "У меня уже есть аккаунт",
            textDecoration = TextDecoration.Underline,
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier
                .padding(top = 10.dp, bottom = 24.dp)
                .clickable {
                    onNavToLoginPage.invoke()
                }
        )

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


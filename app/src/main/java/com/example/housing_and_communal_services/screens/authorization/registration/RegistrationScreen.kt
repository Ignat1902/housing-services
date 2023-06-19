package com.example.housing_and_communal_services.screens.authorization.registration

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
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
import com.example.housing_and_communal_services.data.repositories.AuthRepository
import com.example.housing_and_communal_services.data.repositories.FirebaseRequest
import com.example.housing_and_communal_services.showBars
import com.example.housing_and_communal_services.view_models.LoginViewModel

@Composable
fun RegistrationPage(
    loginViewModel: LoginViewModel? = null,
    onNavToHomePage: () -> Unit,
    onNavToLoginPage: () -> Unit
) {
    showBars(flag = true)
    val request = FirebaseRequest()
    val authRepository = AuthRepository()
    val loginUiState = loginViewModel?.loginUiState
    val isError = loginUiState?.signUpError != null
    val context = LocalContext.current
    val passwordVisibility = remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp),
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .background(MaterialTheme.colorScheme.background)
                .padding(horizontal = 32.dp)
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
            } else
                Spacer(modifier = Modifier.height(8.dp))


            //Поле для ввода имени
            OutlinedTextField(
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                value = loginUiState?.name ?: "",
                onValueChange = {
                    loginViewModel?.onNameChange(it)
                },
                label = { Text(text = "Имя") },
                isError = isError,
                placeholder = {
                    Text(
                        text = "Введите имя",
                        maxLines = 1
                    )
                },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
            )

            //Поле для ввода фамилии
            OutlinedTextField(
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                value = loginUiState?.surname ?: "",
                onValueChange = {
                    loginViewModel?.onSurnameChange(it)
                },
                label = { Text(text = "Фамилия") },
                isError = isError,
                placeholder = {
                    Text(
                        text = "Введите фамилию",
                        maxLines = 1
                    )
                },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
            )

            //Поле для ввода отчества
            OutlinedTextField(
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                value = loginUiState?.lastName?: "",
                onValueChange = {
                    loginViewModel?.onLastNameChange(it)
                },
                label = { Text(text = "Отчество") },
                isError = isError,
                placeholder = {
                    Text(
                        text = "Введите отчество",
                        maxLines = 1
                    )
                },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
            )

            //Поле для ввода номера телефона
            OutlinedTextField(
                value = loginUiState?.phoneNumber ?: "",
                onValueChange = { newValue ->
                    // Очищаем значение от символов, которые нельзя вводить
                    val filteredText = newValue.filter { it.isDigit() }
                    if (filteredText.length <= 10) {
                        // Если символов не больше 10, то обновляем поле ввода
                        loginViewModel?.onPhoneNumberChange(filteredText)
                    }
                },
                isError = isError,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                leadingIcon = { Text(text = "+7") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
            )

            //Поле для ввода Email
            OutlinedTextField(
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                value = loginUiState?.loginSignUp ?: "",
                onValueChange = {
                    loginViewModel?.onLoginChangeSignUp(it)
                },
                label = { Text(text = "Email") },
                isError = isError,
                placeholder = {
                    Text(
                        text = "Введите Email",
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
                isError = isError,
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
                isError = isError,
                onValueChange = {
                    loginViewModel?.onConfirmPasswordChangeSignUp(it)
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                label = { Text(text = "Подтвердите пароль") },
                placeholder = { Text(text = "Введите пароль") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth(),
                visualTransformation = if (passwordVisibility.value) VisualTransformation.None else PasswordVisualTransformation(),
            )
            Spacer(modifier = Modifier.height(100.dp))

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

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(horizontal = 32.dp)
                .align(Alignment.BottomCenter)
        ) {
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
        }
    }
}

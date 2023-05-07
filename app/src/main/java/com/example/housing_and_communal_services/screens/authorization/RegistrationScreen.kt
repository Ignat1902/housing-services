package com.example.housing_and_communal_services.screens.authorization

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.*
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.compose.Housing_and_communal_servicesTheme
import com.example.housing_and_communal_services.R
import com.example.housing_and_communal_services.navigation.Screen
import com.example.housing_and_communal_services.showBars

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationPage(navController: NavController) {
    showBars(flag = true)
    val emailValue = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val confirmationPassword = remember { mutableStateOf("") }
    val passwordVisibility = remember { mutableStateOf(false) }
    val numberAccount = remember { mutableStateOf("") }
    val name = remember { mutableStateOf("") }
    val surname = remember { mutableStateOf("") }
    val patronymic = remember { mutableStateOf("") }
    val telephone = remember { mutableStateOf("+7") }

    val scrollState = rememberScrollState()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
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
        Spacer(Modifier.height(24.dp))
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            //Номер личного счета
            OutlinedTextField(
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                value = numberAccount.value,
                trailingIcon = {
                    IconButton(onClick = { TODO() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_info_24),
                            contentDescription = "info",
                        )
                    }
                },
                onValueChange = {
                    numberAccount.value = it
                },
                label = { Text(text = "Номер личного счета") },
                placeholder = {
                    Text(
                        text = "Введите номер ЛС",
                        maxLines = 1
                    )
                },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth(),
            )

            //Имя
            OutlinedTextField(
                value = name.value,
                onValueChange = {
                    name.value = it
                },
                label = { Text(text = "Имя*") },
                placeholder = { Text(text = "Введите своё имя", overflow = TextOverflow.Visible) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                modifier = Modifier
                    .fillMaxWidth()
            )

            //Фамилия
            OutlinedTextField(
                value = surname.value,
                onValueChange = {
                    surname.value = it
                },
                label = { Text(text = "Фамилия*") },
                placeholder = { Text(text = "Введите свою фамилию") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                modifier = Modifier
                    .fillMaxWidth()
            )

            //Отчество
            OutlinedTextField(
                value = patronymic.value,
                onValueChange = {
                    patronymic.value = it
                },
                label = { Text(text = "Отчество") },
                placeholder = { Text(text = "Необязательно") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                modifier = Modifier
                    .fillMaxWidth()
            )

            //Телефон
            OutlinedTextField(
                value = telephone.value,
                onValueChange = {
                    telephone.value = it
                },
                label = { Text(text = "Телефон*") },
                placeholder = { Text(text = "+7") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                modifier = Modifier
                    .fillMaxWidth()
            )
            //E-mail
            OutlinedTextField(
                value = emailValue.value,
                onValueChange = {
                    emailValue.value = it
                },
                label = { Text(text = "E-mail*") },
                placeholder = { Text(text = "Введите E-mail") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier
                    .fillMaxWidth()
            )

            //Пароль
            OutlinedTextField(
                value = password.value,
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
                    password.value = it
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                label = { Text(text = "Пароль*") },
                supportingText = { Text(text = "Введите не менее 6 символов") },
                placeholder = { Text(text = "Введите пароль") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth(),
                visualTransformation = if (passwordVisibility.value) VisualTransformation.None else PasswordVisualTransformation(),
            )

            //Подтвердите пароль
            OutlinedTextField(
                value = confirmationPassword.value,
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
                    confirmationPassword.value = it
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                label = { Text(text = "Подтвердите пароль*") },
                placeholder = { Text(text = "Введите пароль") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth(),
                visualTransformation = if (passwordVisibility.value) VisualTransformation.None else PasswordVisualTransformation(),
            )

        }
        //Кнопка регистрации
        Button(
            onClick = {},
            enabled = false,
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
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier
                .padding(vertical = 10.dp)
                .clickable {
                    navController.navigate(Screen.Login.route)
                }
        )

    }

}

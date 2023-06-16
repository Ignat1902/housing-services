package com.example.housing_and_communal_services.screens.authorization


import androidx.compose.foundation.background
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
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.housing_and_communal_services.repository.AuthRepository
import com.example.housing_and_communal_services.repository.FirebaseRequest
import com.example.housing_and_communal_services.repository.User
import com.example.housing_and_communal_services.showBars

@Composable
fun PersonalDataEntryScreen(
    onNextPage: () -> Unit
) {
    val context = LocalContext.current
    showBars(flag = true)

    var isNameValid by remember { mutableStateOf(false) }
    var isSurnameValid by remember { mutableStateOf(false) }
    var isLastNameValid by remember { mutableStateOf(false) }
    var isEmailValid by remember { mutableStateOf(false) }

    val name = remember {
        mutableStateOf("")
    }
    val surname = remember {
        mutableStateOf("")
    }
    val lastName = remember {
        mutableStateOf("")
    }
    val email = remember {
        mutableStateOf("")
    }

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
            "Почти готово!",
            modifier = Modifier.padding(top = 24.dp),
            style = MaterialTheme.typography.displayMedium,
            color = MaterialTheme.colorScheme.onBackground
        )

        Text(
            "Заполните поля с личной информацией и укажите электронную почту",
            modifier = Modifier.padding(top = 8.dp),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.outline
        )


        OutlinedTextField(
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            value = name.value,
            onValueChange = {
                name.value = it
            },
            label = { Text(text = "Имя") },
            placeholder = {
                Text(
                    text = "Введите ваше имя",
                    maxLines = 1
                )
            },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth(),
            isError = isNameValid
        )


        OutlinedTextField(
            value = surname.value,
            onValueChange = {
                surname.value = it
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            label = { Text(text = "Фамилия") },
            placeholder = {
                Text(
                    text = "Введите вашу фамилию",
                    maxLines = 1
                )
            },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth(),
            isError = isSurnameValid
        )

        OutlinedTextField(
            value = lastName.value,
            onValueChange = {
                lastName.value = it
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            label = { Text(text = "Отчество") },
            placeholder = { Text(text = "Введите ваше отчество") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth(),
            isError = isLastNameValid
        )

        //"Электронная почта"
        OutlinedTextField(
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            value = email.value,
            onValueChange = {
                email.value = it
            },
            label = { Text(text = "Email") },
            placeholder = {
                Text(
                    text = "Введите email адрес",
                    maxLines = 1
                )
            },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth(),
            isError = isEmailValid
        )

        //Кнопка регистрации
        Button(
            onClick = {
                if (name.value == ""){
                    isNameValid = true
                }
                else if (surname.value == ""){
                    isSurnameValid = true
                }
                else if (lastName.value == ""){
                    isLastNameValid = true
                }
                else if (email.value == ""){
                    isEmailValid = true
                }
                else{
                    val repository = AuthRepository()
                    val user = User(name.value,surname.value, lastName.value,email.value,repository.getUserId())
                    val request = FirebaseRequest()
                    request.createUserDocument(user,context)
                    onNextPage.invoke()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(top = 24.dp),
            shape = RoundedCornerShape(4.dp),

            ) {
            Text(
                text = "Завершить регистрацию",
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding()
            )
        }

    }
}

@Preview
@Composable
fun Pr() {
    //PersonalDataEntryScreen()
}
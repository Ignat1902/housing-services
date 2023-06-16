package com.example.housing_and_communal_services.screens.screen

import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.Housing_and_communal_servicesTheme
import com.example.housing_and_communal_services.MainActivity
import com.example.housing_and_communal_services.R
import com.example.housing_and_communal_services.repository.AuthRepository
import com.example.housing_and_communal_services.repository.User
import com.example.housing_and_communal_services.showBars
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ProfileInfoScreen() {
    showBars(flag = true)
    val context = LocalContext.current
    val intent = remember { Intent(context, MainActivity::class.java) }
    Scaffold(
        content = { padding ->
            BoxWithConstraints(
                Modifier.padding(padding),
            ) {
                Column(
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            modifier = Modifier.size(98.dp),
                            painter = painterResource(id = R.drawable.baseline_account_circle_24),
                            contentDescription = null
                        )
                    }
                    DisplayUserData()
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Button(
                            onClick = {
                                Firebase.auth.signOut()
                                context.startActivity(intent)
                            },
                            modifier = Modifier
                                .wrapContentHeight()
                                .padding(horizontal = 24.dp, vertical = 12.dp),
                            shape = RoundedCornerShape(4.dp),

                            ) {
                            Text(
                                text = "Выход",
                                color = MaterialTheme.colorScheme.onPrimary,
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(vertical = 4.dp, horizontal = 12.dp)
                            )
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun DisplayUserData() {
    var user by remember { mutableStateOf<User?>(null) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            val authRepository = AuthRepository()
            user = authRepository.fetchUserById()
        }
    }

    // Здесь вы можете отобразить данные пользователя с помощью Jetpack Compose
    DisplayUser(user = user)
}

@Composable
fun DisplayUser(user: User?) {
    if (user != null) {
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
        ) {
            user.name?.let { OutputOfUserInformation(name = "Имя", value = it) }
            user.surname?.let { OutputOfUserInformation(name = "Фамилия", value = it) }
            user.lastName?.let { OutputOfUserInformation(name = "Отчество", value = it) }
            user.email?.let { OutputOfUserInformation(name = "E-mail", value = it) }

        }
    }
}

@Composable
fun OutputOfUserInformation(name: String, value: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.outline
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge
        )
    }
    Spacer(modifier = Modifier.height(12.dp))
    Divider()
    Spacer(modifier = Modifier.height(24.dp))
}

//Функция для редактирования поля
@Composable
fun EditableTextWithFAB() {
    val isEditing = remember { mutableStateOf(false) }
    val textValue = remember { mutableStateOf(TextFieldValue("Начальный текст")) }

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (isEditing.value) {
            OutlinedTextField(
                value = textValue.value,
                onValueChange = { newValue -> textValue.value = newValue },
                label = { Text("Редактирование текста") },
                modifier = Modifier.padding(16.dp)
            )
        } else {
            Text(
                textValue.value.text,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(16.dp)
            )
        }

        FloatingActionButton(
            onClick = { isEditing.value = !isEditing.value },
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = Color.White
        ) {
            Icon(Icons.Filled.Edit, contentDescription = "Редактировать")
        }
    }
}

@Composable
@Preview
fun PreviewProfile() {
    Housing_and_communal_servicesTheme {

    }
}

package com.example.housing_and_communal_services.screens.authorization

import android.widget.Scroller
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.*
import androidx.compose.material3.AlertDialogDefaults.shape
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCompositionContext
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.compose.Housing_and_communal_servicesTheme
import com.example.compose.md_theme_dark_onBackground
import com.example.housing_and_communal_services.R
import com.example.housing_and_communal_services.navigation.Screen
import com.example.housing_and_communal_services.showBars

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginPage(navController: NavController) {
    showBars(flag = true)

    val emailValue = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
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
        Spacer(Modifier.height(96.dp))

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.verticalScroll(rememberScrollState())
        ) {
            OutlinedTextField(
                value = emailValue.value,
                onValueChange = {
                    emailValue.value = it
                },
                label = { Text(text = "Логин") },
                placeholder = { Text(text = "Введите логин") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier
                    .fillMaxWidth()
            )
            Spacer(Modifier.height(24.dp))
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
                label = { Text(text = "Пароль") },
                placeholder = { Text(text = "Введите пароль") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth(),
                visualTransformation = if (passwordVisibility.value) VisualTransformation.None else PasswordVisualTransformation(),
            )
            Spacer(Modifier.height(48.dp))
            Button(
                enabled = false,
                onClick = { /*TODO*/ },
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
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier
                    .padding(top = 10.dp)
                    .clickable {
                        navController.navigate(Screen.Registration.route)
                    }
            )

        }
    }
}

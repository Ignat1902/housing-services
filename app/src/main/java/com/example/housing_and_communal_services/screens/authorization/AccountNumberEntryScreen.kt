package com.example.housing_and_communal_services.screens.authorization

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.housing_and_communal_services.R
import com.example.housing_and_communal_services.repository.FirebaseRequest
import com.example.housing_and_communal_services.showBars
import kotlinx.coroutines.tasks.await


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AccountNumberEntryPage(
    onNextPage: () -> Unit
) {
    showBars(flag = true)
    val disabledButton = remember {
        mutableStateOf(true)
    }
    var accountNumber by remember {
        mutableStateOf("")
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp),
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 32.dp)
    ) {
        Text(
            "Введите номер личного счета, указанный на квитанции об оплате",
            modifier = Modifier.padding(top = 24.dp),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground
        )


        OutlinedTextField(
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            value = accountNumber,
            onValueChange = {
                accountNumber = it
            },
            label = { Text(text = "Номер ЛС") },
            placeholder = {
                Text(
                    text = "Введите номер ЛС",
                    maxLines = 1
                )
            },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
        )

        if (accountNumber.length >= 6) {
            Text(
                "По данному лицевому счету был найден следующий адрес:",
                modifier = Modifier.padding(top = 24.dp),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
            Card(
                border = ButtonDefaults.outlinedButtonBorder,
                colors = CardDefaults.outlinedCardColors(),
                shape = RoundedCornerShape(8.dp),
            ) {
                Column(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        Modifier
                            .padding(
                                vertical = 16.dp
                            )
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .background(MaterialTheme.colorScheme.primary, CircleShape)
                                .padding(8.dp)

                        ) {
                            Icon(
                                modifier = Modifier.size(24.dp),
                                tint = MaterialTheme.colorScheme.onPrimary,
                                painter = painterResource(id = R.drawable.apartment_48px),
                                contentDescription = "Icon"
                            )

                        }
                        Column(
                            verticalArrangement = Arrangement.Center
                        ) {
                            /*Text(
                                text = "Квартира",
                                style = MaterialTheme.typography.titleMedium,
                                textAlign = TextAlign.Center
                            )*/
                            AccountAddressText(accountNumber = accountNumber.toInt())
                        }
                    }
                }
            }
        }

        if (accountNumber.length >= 6) {
            Button(
                onClick = {
                    onNextPage.invoke()
                },
                enabled = disabledButton.value,
                modifier = Modifier
                    .wrapContentHeight()
                    .padding(horizontal = 24.dp, vertical = 24.dp),
                shape = RoundedCornerShape(4.dp),

                ) {
                Text(
                    text = "Далее",
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(vertical = 4.dp, horizontal = 12.dp)
                )
            }
        }

    }
}

@Composable
fun AccountAddressText(accountNumber: Int) {
    val accountAddress = remember {
        mutableStateOf("Загрузка...")
    }

    LaunchedEffect(true) {
        try {
            val querySnapshot = FirebaseRequest().getAccountAddress(accountNumber).await()
            if (!querySnapshot.isEmpty) {
                val documentSnapshot = querySnapshot.documents[0]
                val address = documentSnapshot.getString("adress")
                accountAddress.value = address ?: "Адрес не найден"
            }
        } catch (e: Exception) {
            accountAddress.value = "Ошибка получения адреса: ${e.message}"
        }
    }

    Text(
        text = accountAddress.value,
        style = MaterialTheme.typography.bodyMedium,
        textAlign = TextAlign.Start,
        modifier = Modifier.fillMaxWidth()
    )
}
